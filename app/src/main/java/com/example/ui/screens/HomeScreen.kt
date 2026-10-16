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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ContentType
import com.example.data.model.PromptTutorial
import com.example.ui.components.HeroTrendBanner
import com.example.ui.components.PromptCard
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.Navy700
import com.example.ui.theme.Navy800
import com.example.ui.theme.Navy900
import com.example.ui.theme.Navy950
import com.example.ui.theme.NeonPurple
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun HomeScreen(
    prompts: List<PromptTutorial>,
    isHindi: Boolean,
    savedPromptIds: Set<String>,
    onSaveToggle: (String) -> Unit,
    onPromptClick: (String) -> Unit,
    onNavigateToExplore: (category: String?) -> Unit,
    modifier: Modifier = Modifier
) {
    val featuredPrompt = prompts.firstOrNull()

    // Categorized lists
    val photoPrompts = prompts.filter { it.contentType == ContentType.PHOTO }
    val videoPrompts = prompts.filter { it.contentType == ContentType.VIDEO }
    val editingPrompts = prompts.filter { it.contentType == ContentType.EDITING }
    val royalAnd3DPrompts = prompts.filter {
        it.category.contains("Royal", ignoreCase = true) || it.category.contains("3D", ignoreCase = true)
    }
    val reelsPrompts = prompts.filter {
        it.category.contains("Cinematic", ignoreCase = true) || it.category.contains("Captions", ignoreCase = true)
    }
    val savedPrompts = prompts.filter { savedPromptIds.contains(it.id) }

    val quickCategories = listOf(
        Pair("All", if (isHindi) "सभी" else "All"),
        Pair("Royal & 3D Photo Styles", if (isHindi) "रॉयल एवं 3D" else "Royal & 3D"),
        Pair("Photo to Video Tutorials", if (isHindi) "फोटो से वीडियो" else "Photo to Video"),
        Pair("Cinematic Reels", if (isHindi) "सिनेमैटिक रील्स" else "Cinematic"),
        Pair("AI Photo Editing", if (isHindi) "फोटो एडिटिंग" else "Editing"),
        Pair("Festival Photo Styles", if (isHindi) "त्यौहार स्टाइल्स" else "Festivals"),
        Pair("Product Photography", if (isHindi) "प्रोडक्ट फोटो" else "Product")
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Navy950)
            .testTag("home_screen_scroll"),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Search bar button (triggers navigation to explore)
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Navy800)
                    .border(1.dp, Navy700, RoundedCornerShape(14.dp))
                    .clickable { onNavigateToExplore(null) }
                    .padding(horizontal = 14.dp, vertical = 12.dp)
                    .testTag("home_search_bar")
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = ElectricBlue,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = if (isHindi) "प्रॉम्प्ट्स, टूल्स या स्टाइल खोजें..." else "Search prompts, tools, or styles...",
                        style = MaterialTheme.typography.bodyMedium.copy(color = TextMuted)
                    )
                }
            }
        }

        // Quick Category Filter Row
        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(quickCategories) { (catKey, catLabel) ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Navy800)
                            .border(1.dp, Navy700, RoundedCornerShape(20.dp))
                            .clickable { onNavigateToExplore(if (catKey == "All") null else catKey) }
                            .padding(horizontal = 12.dp, vertical = 7.dp)
                    ) {
                        Text(
                            text = catLabel,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = TextWhite,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }
        }

        // Featured Trend of the Day
        if (featuredPrompt != null) {
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                    HeroTrendBanner(
                        prompt = featuredPrompt,
                        isHindi = isHindi,
                        onClick = { onPromptClick(featuredPrompt.id) }
                    )
                }
            }
        }

        // Section: Trending Photo Prompts
        item {
            SectionHeader(
                title = if (isHindi) "ट्रेंडिंग फोटो प्रॉम्प्ट्स" else "Trending Photo Prompts",
                subtitle = if (isHindi) "Instagram पर सबसे ज्यादा शेयर होने वाले फोटो स्टाइल्स" else "Top requested photo styles for feeds & stories",
                onSeeAllClick = { onNavigateToExplore("Photo Prompt") }
            )
        }
        item {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(photoPrompts) { prompt ->
                    Box(modifier = Modifier.width(310.dp)) {
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

        // Section: Trending Video Prompts & Photo to Video
        item {
            Spacer(modifier = Modifier.height(16.dp))
            SectionHeader(
                title = if (isHindi) "फोटो से वीडियो एवं रील्स प्रॉम्प्ट्स" else "Photo to Video & Reels Motion",
                subtitle = if (isHindi) "तस्वीर में जान डालने वाले मोशन निर्देश" else "Prompts to transform still portraits into dynamic videos",
                onSeeAllClick = { onNavigateToExplore("Video Prompt") }
            )
        }
        item {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(videoPrompts) { prompt ->
                    Box(modifier = Modifier.width(310.dp)) {
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

        // Section: Royal & 3D Photo Styles
        item {
            Spacer(modifier = Modifier.height(16.dp))
            SectionHeader(
                title = if (isHindi) "रॉयल और 3D फोटो स्टाइल्स" else "Royal & 3D Photo Styles",
                subtitle = if (isHindi) "सिंहासन, विंग्स और 3D अवतार" else "Thrones, neon wings, and cartoon avatars",
                onSeeAllClick = { onNavigateToExplore("Royal & 3D Photo Styles") }
            )
        }
        item {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(royalAnd3DPrompts) { prompt ->
                    Box(modifier = Modifier.width(310.dp)) {
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

        // Section: AI Photo Editing & Color Grading
        if (editingPrompts.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                SectionHeader(
                    title = if (isHindi) "AI फोटो एडिटिंग एवं कलर ग्रेडिंग" else "AI Photo Editing & Color Presets",
                    subtitle = if (isHindi) "मूडी टोन्स और मैगज़ीन लुक देने की विधि" else "Film tones and cinematic color adjustments",
                    onSeeAllClick = { onNavigateToExplore("Photo Editing") }
                )
            }
            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(editingPrompts) { prompt ->
                        Box(modifier = Modifier.width(310.dp)) {
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

        // Section: Cinematic Reels Ideas
        item {
            Spacer(modifier = Modifier.height(16.dp))
            SectionHeader(
                title = if (isHindi) "इंस्टाग्राम रील्स आइडियाज और हुक्स" else "Instagram Reels Ideas & Hooks",
                subtitle = if (isHindi) "व्यूज बढ़ाने वाले हुक्स और हैशटैग गाइड" else "Engagement hooks, caption blueprints, and tags",
                onSeeAllClick = { onNavigateToExplore("Cinematic Reels") }
            )
        }
        item {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(reelsPrompts) { prompt ->
                    Box(modifier = Modifier.width(310.dp)) {
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

        // Section: Saved Prompts Quick Preview
        if (savedPrompts.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                SectionHeader(
                    title = if (isHindi) "आपके सेव किए गए प्रॉम्प्ट्स" else "Your Saved Prompts",
                    subtitle = if (isHindi) "ऑफलाइन उपयोग के लिए सुरक्षित ट्यूटोरियल्स" else "Bookmarked for easy offline access",
                    onSeeAllClick = { onNavigateToExplore(null) }
                )
            }
            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(savedPrompts) { prompt ->
                        Box(modifier = Modifier.width(310.dp)) {
                            PromptCard(
                                prompt = prompt,
                                isHindi = isHindi,
                                isSaved = true,
                                onSaveToggle = { onSaveToggle(prompt.id) },
                                onClick = { onPromptClick(prompt.id) }
                            )
                        }
                    }
                }
            }
        }

        // Educational Disclaimer Notice Banner
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Navy800)
                    .border(1.dp, Navy700, RoundedCornerShape(12.dp))
                    .padding(14.dp)
            ) {
                Column {
                    Text(
                        text = if (isHindi) "⚠️ महत्वपूर्ण सूचना (Educational Library)" else "⚠️ Important Notice (Educational Library)",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = ElectricBlue,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (isHindi)
                            "HR AI Studio केवल शैक्षिक प्रॉम्प्ट्स और ट्यूटोरियल्स प्रदान करता है। यह ऐप स्वयं कोई इमेज या वीडियो जनरेट नहीं करता है। सभी ट्रेंड्स और टूल्स वेरिफाइड आधिकारिक साइटों पर उपलब्ध हैं।"
                        else
                            "HR AI Studio is an educational prompt library and tutorial guide. It does not generate images or videos directly. All prompts and links guide you to official verified external tools.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TextMuted,
                            lineHeight = 17.sp
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    subtitle: String,
    onSeeAllClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TextMuted,
                    fontSize = 11.sp
                )
            )
        }

        Row(
            modifier = Modifier
                .clickable { onSeeAllClick() }
                .padding(vertical = 4.dp, horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "See All",
                tint = ElectricBlue,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
