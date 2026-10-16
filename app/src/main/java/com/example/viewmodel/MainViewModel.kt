package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.db.AppDatabase
import com.example.data.db.SavedPromptEntity
import com.example.data.model.ContentType
import com.example.data.model.Difficulty
import com.example.data.model.ExternalTool
import com.example.data.model.PromptTutorial
import com.example.data.repository.IPromptRepository
import com.example.data.repository.LocalPromptRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class NavigationTab(val labelEn: String, val labelHi: String) {
    HOME("Home", "होम"),
    EXPLORE("Explore", "खोजें"),
    TOOLS("Tools", "टूल्स"),
    SAVED("Saved", "सेव किए"),
    SETTINGS("Settings", "सेटिंग्स")
}

enum class SortOption(val labelEn: String, val labelHi: String) {
    LATEST("Latest Added", "नवीनतम"),
    ALPHABETICAL("A to Z", "नाम अनुसार"),
    DIFFICULTY("Difficulty", "कठिनाई अनुसार")
}

data class PromptFilterCriteria(
    val query: String = "",
    val category: String = "All",
    val contentType: ContentType? = null,
    val difficulty: Difficulty? = null,
    val sortOption: SortOption = SortOption.LATEST
)

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: IPromptRepository = LocalPromptRepository()
    private val savedPromptDao = AppDatabase.getDatabase(application).savedPromptDao()

    // Language state: true = Hindi (Default), false = English
    private val _isHindi = MutableStateFlow(true)
    val isHindi: StateFlow<Boolean> = _isHindi.asStateFlow()

    fun toggleLanguage() {
        _isHindi.value = !_isHindi.value
    }

    fun setLanguage(hindi: Boolean) {
        _isHindi.value = hindi
    }

    // Navigation Tab
    private val _currentTab = MutableStateFlow(NavigationTab.HOME)
    val currentTab: StateFlow<NavigationTab> = _currentTab.asStateFlow()

    fun selectTab(tab: NavigationTab) {
        _currentTab.value = tab
    }

    // Detail view state: ID of active prompt (or null for main list)
    private val _selectedPromptId = MutableStateFlow<String?>(null)
    val selectedPromptId: StateFlow<String?> = _selectedPromptId.asStateFlow()

    fun openDetail(promptId: String) {
        _selectedPromptId.value = promptId
    }

    fun closeDetail() {
        _selectedPromptId.value = null
    }

    // Search Query & Filters
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _selectedContentType = MutableStateFlow<ContentType?>(null)
    val selectedContentType: StateFlow<ContentType?> = _selectedContentType.asStateFlow()

    private val _selectedDifficulty = MutableStateFlow<Difficulty?>(null)
    val selectedDifficulty: StateFlow<Difficulty?> = _selectedDifficulty.asStateFlow()

    private val _sortOption = MutableStateFlow(SortOption.LATEST)
    val sortOption: StateFlow<SortOption> = _sortOption.asStateFlow()

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }

    fun selectContentType(contentType: ContentType?) {
        _selectedContentType.value = contentType
    }

    fun selectDifficulty(difficulty: Difficulty?) {
        _selectedDifficulty.value = difficulty
    }

    fun selectSortOption(sort: SortOption) {
        _sortOption.value = sort
    }

    // All Prompts from repository
    private val _allPrompts = MutableStateFlow<List<PromptTutorial>>(emptyList())
    val allPrompts: StateFlow<List<PromptTutorial>> = _allPrompts.asStateFlow()

    // External Tools
    private val _externalTools = MutableStateFlow<List<ExternalTool>>(emptyList())
    val externalTools: StateFlow<List<ExternalTool>> = _externalTools.asStateFlow()

    // Saved Prompt IDs from Room
    val savedPromptIds: StateFlow<Set<String>> = savedPromptDao.getAllSaved()
        .map { list -> list.map { it.promptId }.toSet() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    init {
        viewModelScope.launch {
            repository.getAllPrompts().collect {
                _allPrompts.value = it
            }
        }
        viewModelScope.launch {
            repository.getAllExternalTools().collect {
                _externalTools.value = it
            }
        }
    }

    // Filter criteria flow
    private val filterCriteria = combine(
        _searchQuery,
        _selectedCategory,
        _selectedContentType,
        _selectedDifficulty,
        _sortOption
    ) { q, cat, type, diff, sort ->
        PromptFilterCriteria(
            query = q,
            category = cat,
            contentType = type,
            difficulty = diff,
            sortOption = sort
        )
    }

    // Filtered & Sorted prompts for Explore Screen
    val filteredPrompts: StateFlow<List<PromptTutorial>> = combine(
        _allPrompts,
        filterCriteria
    ) { prompts, criteria ->
        var result = prompts

        if (criteria.query.isNotBlank()) {
            val q = criteria.query.trim().lowercase()
            result = result.filter { prompt ->
                prompt.titleEn.lowercase().contains(q) ||
                prompt.titleHi.lowercase().contains(q) ||
                prompt.promptText.lowercase().contains(q) ||
                prompt.category.lowercase().contains(q) ||
                prompt.categoryHi.lowercase().contains(q) ||
                prompt.hashtags.any { tag -> tag.lowercase().contains(q) } ||
                prompt.supportedTools.any { tool -> tool.name.lowercase().contains(q) }
            }
        }

        if (criteria.category != "All" && criteria.category != "सभी" && criteria.category != "सभी श्रेणियां") {
            result = result.filter { prompt ->
                prompt.category.equals(criteria.category, ignoreCase = true) ||
                prompt.categoryHi.equals(criteria.category, ignoreCase = true)
            }
        }

        if (criteria.contentType != null) {
            result = result.filter { it.contentType == criteria.contentType }
        }

        if (criteria.difficulty != null) {
            result = result.filter { it.difficulty == criteria.difficulty }
        }

        when (criteria.sortOption) {
            SortOption.LATEST -> result
            SortOption.ALPHABETICAL -> result.sortedBy { it.titleEn }
            SortOption.DIFFICULTY -> result.sortedBy { it.difficulty.ordinal }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Saved Prompts List
    val savedPromptsList: StateFlow<List<PromptTutorial>> = combine(
        _allPrompts,
        savedPromptIds
    ) { prompts, savedIds ->
        prompts.filter { savedIds.contains(it.id) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun toggleSave(promptId: String) {
        viewModelScope.launch {
            if (savedPromptIds.value.contains(promptId)) {
                savedPromptDao.removePrompt(promptId)
            } else {
                savedPromptDao.savePrompt(SavedPromptEntity(promptId))
            }
        }
    }

    fun isPromptSaved(promptId: String): Boolean {
        return savedPromptIds.value.contains(promptId)
    }
}
