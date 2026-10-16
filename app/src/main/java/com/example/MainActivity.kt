package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Apps
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.BrandHeader
import com.example.ui.screens.DetailScreen
import com.example.ui.screens.ExploreScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.SavedScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.ToolsScreen
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.HRAIStudioTheme
import com.example.ui.theme.Navy700
import com.example.ui.theme.Navy800
import com.example.ui.theme.Navy900
import com.example.ui.theme.Navy950
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import com.example.viewmodel.MainViewModel
import com.example.viewmodel.NavigationTab

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HRAIStudioTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp(viewModel: MainViewModel = viewModel()) {
    val isHindi by viewModel.isHindi.collectAsStateWithLifecycle()
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val selectedPromptId by viewModel.selectedPromptId.collectAsStateWithLifecycle()

    val allPrompts by viewModel.allPrompts.collectAsStateWithLifecycle()
    val filteredPrompts by viewModel.filteredPrompts.collectAsStateWithLifecycle()
    val savedPrompts by viewModel.savedPromptsList.collectAsStateWithLifecycle()
    val savedPromptIds by viewModel.savedPromptIds.collectAsStateWithLifecycle()
    val externalTools by viewModel.externalTools.collectAsStateWithLifecycle()

    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val selectedContentType by viewModel.selectedContentType.collectAsStateWithLifecycle()
    val selectedDifficulty by viewModel.selectedDifficulty.collectAsStateWithLifecycle()
    val sortOption by viewModel.sortOption.collectAsStateWithLifecycle()

    // If a prompt is selected, show DetailScreen
    val activePrompt = allPrompts.find { it.id == selectedPromptId }

    if (activePrompt != null) {
        DetailScreen(
            prompt = activePrompt,
            allPrompts = allPrompts,
            isHindi = isHindi,
            isSaved = savedPromptIds.contains(activePrompt.id),
            onSaveToggle = { viewModel.toggleSave(activePrompt.id) },
            onBack = { viewModel.closeDetail() },
            onRelatedPromptClick = { newId -> viewModel.openDetail(newId) }
        )
    } else {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            contentWindowInsets = WindowInsets.safeDrawing,
            topBar = {
                BrandHeader(
                    isHindi = isHindi,
                    onToggleLanguage = { viewModel.toggleLanguage() }
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = Navy950,
                    tonalElevation = 8.dp,
                    modifier = Modifier.border(0.5.dp, Navy700)
                ) {
                    NavigationTab.values().forEach { tab ->
                        val isSelected = currentTab == tab
                        val icon = when (tab) {
                            NavigationTab.HOME -> if (isSelected) Icons.Default.Home else Icons.Outlined.Home
                            NavigationTab.EXPLORE -> if (isSelected) Icons.Default.Explore else Icons.Outlined.Explore
                            NavigationTab.TOOLS -> if (isSelected) Icons.Default.Apps else Icons.Outlined.Apps
                            NavigationTab.SAVED -> if (isSelected) Icons.Default.Bookmark else Icons.Default.BookmarkBorder
                            NavigationTab.SETTINGS -> if (isSelected) Icons.Default.Settings else Icons.Outlined.Settings
                        }

                        val tabLabel = if (isHindi) tab.labelHi else tab.labelEn

                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { viewModel.selectTab(tab) },
                            icon = {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = tabLabel,
                                    modifier = Modifier.size(22.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = tabLabel,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 11.sp
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Navy950,
                                selectedTextColor = ElectricBlue,
                                indicatorColor = ElectricBlue,
                                unselectedIconColor = TextMuted,
                                unselectedTextColor = TextMuted
                            ),
                            modifier = Modifier.testTag("nav_tab_${tab.name.lowercase()}")
                        )
                    }
                }
            },
            containerColor = Navy950
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Navy950)
            ) {
                AnimatedContent(
                    targetState = currentTab,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    label = "tab_animation"
                ) { targetTab ->
                    when (targetTab) {
                        NavigationTab.HOME -> {
                            HomeScreen(
                                prompts = allPrompts,
                                isHindi = isHindi,
                                savedPromptIds = savedPromptIds,
                                onSaveToggle = { id -> viewModel.toggleSave(id) },
                                onPromptClick = { id -> viewModel.openDetail(id) },
                                onNavigateToExplore = { category ->
                                    if (category != null) {
                                        viewModel.selectCategory(category)
                                    }
                                    viewModel.selectTab(NavigationTab.EXPLORE)
                                }
                            )
                        }

                        NavigationTab.EXPLORE -> {
                            ExploreScreen(
                                prompts = filteredPrompts,
                                searchQuery = searchQuery,
                                onSearchQueryChange = { q -> viewModel.updateSearchQuery(q) },
                                selectedCategory = selectedCategory,
                                onCategorySelect = { cat -> viewModel.selectCategory(cat) },
                                selectedContentType = selectedContentType,
                                onContentTypeSelect = { ct -> viewModel.selectContentType(ct) },
                                selectedDifficulty = selectedDifficulty,
                                onDifficultySelect = { diff -> viewModel.selectDifficulty(diff) },
                                sortOption = sortOption,
                                onSortOptionChange = { sort -> viewModel.selectSortOption(sort) },
                                isHindi = isHindi,
                                savedPromptIds = savedPromptIds,
                                onSaveToggle = { id -> viewModel.toggleSave(id) },
                                onPromptClick = { id -> viewModel.openDetail(id) }
                            )
                        }

                        NavigationTab.TOOLS -> {
                            ToolsScreen(
                                tools = externalTools,
                                isHindi = isHindi
                            )
                        }

                        NavigationTab.SAVED -> {
                            SavedScreen(
                                savedPrompts = savedPrompts,
                                isHindi = isHindi,
                                onRemoveSave = { id -> viewModel.toggleSave(id) },
                                onPromptClick = { id -> viewModel.openDetail(id) },
                                onNavigateHome = { viewModel.selectTab(NavigationTab.EXPLORE) }
                            )
                        }

                        NavigationTab.SETTINGS -> {
                            SettingsScreen(
                                isHindi = isHindi,
                                onSetLanguage = { lang -> viewModel.setLanguage(lang) }
                            )
                        }
                    }
                }
            }
        }
    }
}
