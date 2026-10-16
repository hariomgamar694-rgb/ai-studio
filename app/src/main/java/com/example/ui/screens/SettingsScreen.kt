package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.AccentEmerald
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.Navy700
import com.example.ui.theme.Navy800
import com.example.ui.theme.Navy950
import com.example.ui.theme.NeonPurple
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSubtle
import com.example.ui.theme.TextWhite

@Composable
fun SettingsScreen(
    isHindi: Boolean,
    onSetLanguage: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Navy950)
            .testTag("settings_screen_scroll"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
    ) {
        // App Identity Brand Card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(Navy800, Navy950)
                        )
                    )
                    .border(
                        1.dp,
                        Brush.linearGradient(listOf(ElectricBlue, NeonPurple)),
                        RoundedCornerShape(20.dp)
                    )
                    .padding(20.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_hr_brand_logo),
                        contentDescription = "HR AI Studio Logo",
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(14.dp))
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "HR AI Studio",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = TextWhite
                                )
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = "Sparkle",
                                tint = ElectricBlue,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Text(
                            text = "Instagram Trending Prompts",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = ElectricBlue,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Text(
                            text = if (isHindi) "खोजें. कॉपी करें. बनाएं." else "Discover. Copy. Create.",
                            style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
                        )
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(18.dp)) }

        // Language Selection Section
        item {
            Text(
                text = if (isHindi) "भाषा चुनें (Language)" else "Language Selection",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Navy800),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Navy700)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    // Hindi Option (Default)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isHindi) Navy700 else Navy800)
                            .clickable { onSetLanguage(true) }
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Language,
                                contentDescription = "Hindi",
                                tint = ElectricBlue,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "हिन्दी (डिफ़ॉल्ट)",
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        color = TextWhite,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                                Text(
                                    text = "सभी विवरण और चरण हिन्दी में देखें",
                                    style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
                                )
                            }
                        }

                        if (isHindi) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Active",
                                tint = ElectricBlue
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // English Option
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (!isHindi) Navy700 else Navy800)
                            .clickable { onSetLanguage(false) }
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Language,
                                contentDescription = "English",
                                tint = NeonPurple,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "English",
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        color = TextWhite,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                                Text(
                                    text = "Read all guides and tutorials in English",
                                    style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
                                )
                            }
                        }

                        if (!isHindi) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Active",
                                tint = ElectricBlue
                            )
                        }
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(18.dp)) }

        // Educational Purpose & Policy Transparency
        item {
            Text(
                text = if (isHindi) "पारदर्शिता एवं नीतियां" else "Transparency & Disclaimers",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Navy800),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Navy700)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = "Educational Disclaimer",
                            tint = AccentEmerald,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = if (isHindi) "शैक्षिक लाइब्रेरी घोषणा (Disclaimer)" else "Educational Library Disclaimer",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextWhite
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = if (isHindi)
                            "• HR AI Studio एक शैक्षिक गाइड है। यह ऐप स्वयं कोई इमेज या वीडियो जनरेट नहीं करता है।\n• सभी प्रॉम्प्ट्स यूजर्स को क्रिएटिव विचार प्रदान करने के लिए हैं ताकि वे आधिकारिक बाहरी टूल्स (Meta AI, Microsoft Copilot, Runway आदि) में इनका उपयोग कर सकें।\n• हम कभी भी नकली व्यूज, आंकड़े या रेटिंग्स नहीं दिखाते। प्रत्येक ट्रेंड की अंतिम जांच तिथि स्पष्ट रूप से अंकित है।\n• सभी ट्रेडमार्क, लोगो और ब्रांड नाम उनके संबंधित स्वामियों की संपत्ति हैं।"
                        else
                            "• HR AI Studio is an educational prompt library and tutorial guide. It does NOT generate images or videos directly.\n• All prompts provide creative guidance for users to execute on official external tools (Meta AI, Microsoft Copilot, Runway, etc.).\n• We do not fabricate viral metrics, view counts, or rankings. Each trend features its real verification date.\n• All third-party trademarks and brand names belong to their respective owners.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TextMuted,
                            lineHeight = 20.sp
                        )
                    )
                }
            }
        }

        item { Spacer(modifier = Modifier.height(18.dp)) }

        // Manual Update and Community Guidelines
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Navy800),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Navy700)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Update,
                            contentDescription = "Content updates",
                            tint = ElectricBlue,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = if (isHindi) "नियमित ट्रेंड वेरिफिकेशन" else "Trend Verification & Curation",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextWhite
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = if (isHindi)
                            "हमारी टीम इंस्टाग्राम रील्स और क्रिएटिव कम्युनिटी में नए प्रॉम्प्ट्स की सक्रियता और कार्यप्रणाली का नियमित परीक्षण करती है। नई तकनीकों के जुड़ने पर ऐप का कंटेंट लगातार अपडेट किया जाता है।"
                        else
                            "Our curation verifies prompts for clarity and effectiveness across official web tools. Content is manually verified without automated speculation.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TextMuted,
                            lineHeight = 18.sp
                        )
                    )
                }
            }
        }

        item { Spacer(modifier = Modifier.height(30.dp)) }

        // Version & Copyright
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "HR AI Studio v1.0.0 (Native Android)",
                    style = MaterialTheme.typography.labelSmall.copy(color = TextSubtle)
                )
                Text(
                    text = "Crafted for Creators & Visual Storytellers",
                    style = MaterialTheme.typography.labelSmall.copy(color = TextSubtle)
                )
            }
        }

        item { Spacer(modifier = Modifier.height(80.dp)) }
    }
}
