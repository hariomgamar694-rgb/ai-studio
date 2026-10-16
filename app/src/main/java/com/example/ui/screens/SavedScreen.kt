package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PromptTutorial
import com.example.ui.components.PromptCard
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.Navy950
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun SavedScreen(
    savedPrompts: List<PromptTutorial>,
    isHindi: Boolean,
    onRemoveSave: (String) -> Unit,
    onPromptClick: (String) -> Unit,
    onNavigateHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Navy950)
            .testTag("saved_screen")
    ) {
        if (savedPrompts.isEmpty()) {
            // Empty State
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.BookmarkBorder,
                    contentDescription = "No saved prompts",
                    tint = ElectricBlue,
                    modifier = Modifier.size(64.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = if (isHindi) "कोई प्रॉम्प्ट सेव नहीं किया गया" else "No Saved Prompts Yet",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = TextWhite,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = if (isHindi)
                        "होम या एक्सप्लोर स्क्रीन पर किसी भी प्रॉम्प्ट के बुकमार्क आइकन पर टैप करें ताकि आप उसे कभी भी ऑफलाइन उपयोग कर सकें।"
                    else
                        "Tap the bookmark icon on any prompt card in Home or Explore to save it here for offline reference.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = TextMuted,
                        lineHeight = 22.sp
                    ),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onNavigateHome,
                    colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                    modifier = Modifier.testTag("browse_prompts_button")
                ) {
                    Text(
                        text = if (isHindi) "प्रॉम्प्ट्स खोजें" else "Browse Prompts",
                        color = Navy950,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(top = 16.dp, bottom = 90.dp)
            ) {
                item {
                    Text(
                        text = if (isHindi)
                            "आपके बुकमार्क किए गए ट्यूटोरियल्स (${savedPrompts.size})"
                        else
                            "Your Bookmarked Tutorials (${savedPrompts.size})",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = TextWhite,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }

                items(savedPrompts) { prompt ->
                    Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                        PromptCard(
                            prompt = prompt,
                            isHindi = isHindi,
                            isSaved = true,
                            onSaveToggle = { onRemoveSave(prompt.id) },
                            onClick = { onPromptClick(prompt.id) }
                        )
                    }
                }
            }
        }
    }
}
