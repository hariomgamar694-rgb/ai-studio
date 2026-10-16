package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ContentType
import com.example.data.model.Difficulty
import com.example.data.model.PromptTutorial
import com.example.ui.components.PromptCard
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.Navy700
import com.example.ui.theme.Navy800
import com.example.ui.theme.Navy950
import com.example.ui.theme.NeonPurple
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSubtle
import com.example.ui.theme.TextWhite
import com.example.viewmodel.SortOption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    prompts: List<PromptTutorial>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    selectedCategory: String,
    onCategorySelect: (String) -> Unit,
    selectedContentType: ContentType?,
    onContentTypeSelect: (ContentType?) -> Unit,
    selectedDifficulty: Difficulty?,
    onDifficultySelect: (Difficulty?) -> Unit,
    sortOption: SortOption,
    onSortOptionChange: (SortOption) -> Unit,
    isHindi: Boolean,
    savedPromptIds: Set<String>,
    onSaveToggle: (String) -> Unit,
    onPromptClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = listOf(
        Pair("All", if (isHindi) "सभी श्रेणियां" else "All Categories"),
        Pair("Royal & 3D Photo Styles", if (isHindi) "रॉयल एवं 3D" else "Royal & 3D"),
        Pair("Photo to Video Tutorials", if (isHindi) "फोटो से वीडियो" else "Photo to Video"),
        Pair("Cinematic Reels", if (isHindi) "सिनेमैटिक रील्स" else "Cinematic"),
        Pair("AI Photo Editing", if (isHindi) "फोटो एडिटिंग" else "Editing"),
        Pair("Festival Photo Styles", if (isHindi) "त्यौहार स्टाइल्स" else "Festivals"),
        Pair("Product Photography", if (isHindi) "प्रोडक्ट फोटोग्राफी" else "Product"),
        Pair("Captions and Hashtags Guidance", if (isHindi) "कैप्शन एवं हैशटैग" else "Captions & Tags")
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Navy950)
            .testTag("explore_screen_scroll"),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Search Input Bar
        item {
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("explore_search_field"),
                    placeholder = {
                        Text(
                            text = if (isHindi) "प्रॉम्प्ट्स, कीवर्ड्स, या स्टाइल खोजें..." else "Search prompts, keywords, styles...",
                            color = TextMuted,
                            fontSize = 14.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = ElectricBlue
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { onSearchQueryChange("") }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear search",
                                    tint = TextMuted
                                )
                            }
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Navy800,
                        unfocusedContainerColor = Navy800,
                        focusedBorderColor = ElectricBlue,
                        unfocusedBorderColor = Navy700,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite
                    ),
                    shape = RoundedCornerShape(14.dp),
                    singleLine = true
                )
            }
        }

        // Content Type Filter Chips (Photo / Video / Editing)
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // All Chip
                FilterChip(
                    selected = selectedContentType == null,
                    onClick = { onContentTypeSelect(null) },
                    label = { Text(if (isHindi) "सभी प्रारूप" else "All Formats", fontSize = 12.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = ElectricBlue,
                        selectedLabelColor = Navy950,
                        containerColor = Navy800,
                        labelColor = TextWhite
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = selectedContentType == null,
                        borderColor = Navy700,
                        selectedBorderColor = ElectricBlue
                    )
                )

                ContentType.values().forEach { type ->
                    FilterChip(
                        selected = selectedContentType == type,
                        onClick = { onContentTypeSelect(if (selectedContentType == type) null else type) },
                        label = { Text(if (isHindi) type.labelHi else type.labelEn, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = ElectricBlue,
                            selectedLabelColor = Navy950,
                            containerColor = Navy800,
                            labelColor = TextWhite
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = selectedContentType == type,
                            borderColor = Navy700,
                            selectedBorderColor = ElectricBlue
                        )
                    )
                }
            }
        }

        // Category Horizontal Carousel Filter
        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { (catKey, catLabel) ->
                    val isSelected = selectedCategory.equals(catKey, ignoreCase = true)
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) NeonPurple else Navy800)
                            .border(
                                1.dp,
                                if (isSelected) NeonPurple else Navy700,
                                RoundedCornerShape(20.dp)
                            )
                            .clickable { onCategorySelect(catKey) }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = catLabel,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = if (isSelected) Navy950 else TextWhite,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        )
                    }
                }
            }
        }

        // Sorting & Results Bar
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${prompts.size} ${if (isHindi) "प्रॉम्प्ट्स मिले" else "tutorials found"}",
                    style = MaterialTheme.typography.labelMedium.copy(color = TextMuted)
                )

                // Sort Options toggle
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = "Sort",
                        tint = ElectricBlue,
                        modifier = Modifier.size(14.dp)
                    )

                    SortOption.values().forEach { sort ->
                        val isSortSelected = sortOption == sort
                        Text(
                            text = if (isHindi) sort.labelHi else sort.labelEn,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = if (isSortSelected) ElectricBlue else TextSubtle,
                                fontWeight = if (isSortSelected) FontWeight.Bold else FontWeight.Normal
                            ),
                            modifier = Modifier
                                .clickable { onSortOptionChange(sort) }
                                .padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }

        // Empty Search Results State
        if (prompts.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp, start = 20.dp, end = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "No results",
                            tint = TextMuted,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = if (isHindi) "कोई ट्यूटोरियल नहीं मिला" else "No Tutorials Found",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = TextWhite,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = if (isHindi)
                                "कृपया दूसरा शब्द खोजें या फ़िल्टर रीसेट करें।"
                            else
                                "Try searching for a different keyword or reset filters.",
                            style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
                        )
                    }
                }
            }
        } else {
            // Prompts List
            items(prompts) { prompt ->
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                    PromptCard(
                        prompt = prompt,
                        isHindi = isHindi,
                        isSaved = savedPromptIds.contains(prompt.id),
                        onSaveToggle = { onSaveToggle(prompt.id) },
                        onClick = { onPromptClick(prompt.id) }
                    )
                }
            }
        }
    }
}
