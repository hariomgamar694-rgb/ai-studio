package com.example.data.model

enum class ContentType(val labelEn: String, val labelHi: String) {
    PHOTO("Photo Prompt", "फोटो प्रॉम्प्ट"),
    VIDEO("Video Prompt", "वीडियो प्रॉम्प्ट"),
    EDITING("Photo Editing", "फोटो एडिटिंग")
}

enum class Difficulty(val labelEn: String, val labelHi: String) {
    BEGINNER("Beginner", "शुरुआती"),
    INTERMEDIATE("Intermediate", "मध्यम"),
    ADVANCED("Advanced", "उन्नत")
}

enum class RequiredInput(val labelEn: String, val labelHi: String) {
    TEXT_ONLY("Text Only", "केवल टेक्स्ट"),
    PHOTO_UPLOAD("Photo Upload", "फोटो अपलोड"),
    VIDEO_CLIP("Video Clip", "वीडियो क्लिप")
}

enum class TrendStatus(val labelEn: String, val labelHi: String, val badgeColor: Long) {
    HOT_TREND("Hot Trend", "हॉट ट्रेंड", 0xFFEF4444),
    NEW_TUTORIAL("New Guide", "नया ट्यूटोरियल", 0xFF10B981),
    CLASSIC_STYLE("Popular Style", "पॉपुलर स्टाइल", 0xFF8B5CF6),
    REEL_VIRAL("Reels Viral", "रील्स वायरल", 0xFFF59E0B)
}

data class TutorialStep(
    val stepNumber: Int,
    val titleEn: String,
    val titleHi: String,
    val instructionEn: String,
    val instructionHi: String
)

data class ToolReference(
    val name: String,
    val url: String,
    val isFree: String
)

data class PromptTutorial(
    val id: String,
    val titleEn: String,
    val titleHi: String,
    val category: String,
    val categoryHi: String,
    val contentType: ContentType,
    val difficulty: Difficulty,
    val requiredInput: RequiredInput,
    val trendStatus: TrendStatus,
    val lastVerifiedDate: String,
    val suggestedAspectRatio: String,
    val promptText: String,
    val promptExplanationEn: String,
    val promptExplanationHi: String,
    val visualEffectEn: String,
    val visualEffectHi: String,
    val supportedTools: List<ToolReference>,
    val steps: List<TutorialStep>,
    val proTipsEn: List<String>,
    val proTipsHi: List<String>,
    val commonMistakesEn: List<String>,
    val commonMistakesHi: List<String>,
    val instagramExportGuideEn: String,
    val instagramExportGuideHi: String,
    val hashtags: List<String>,
    val captionsEn: List<String>,
    val captionsHi: List<String>,
    val primaryColorHex: Long = 0xFF38BDF8,
    val secondaryColorHex: Long = 0xFFA855F7,
    val iconName: String = "auto_awesome"
)
