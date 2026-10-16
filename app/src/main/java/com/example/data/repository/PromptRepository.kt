package com.example.data.repository

import com.example.data.model.ContentType
import com.example.data.model.Difficulty
import com.example.data.model.ExternalTool
import com.example.data.model.PromptTutorial
import com.example.data.model.RequiredInput
import com.example.data.model.ToolReference
import com.example.data.model.TrendStatus
import com.example.data.model.TutorialStep
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface IPromptRepository {
    fun getAllPrompts(): Flow<List<PromptTutorial>>
    fun getPromptById(id: String): Flow<PromptTutorial?>
    fun getFeaturedPrompts(): Flow<List<PromptTutorial>>
    fun getPromptsByCategory(category: String): Flow<List<PromptTutorial>>
    fun getPromptsByContentType(contentType: ContentType): Flow<List<PromptTutorial>>
    fun searchPrompts(query: String): Flow<List<PromptTutorial>>
    fun getAllExternalTools(): Flow<List<ExternalTool>>
}

class LocalPromptRepository : IPromptRepository {

    private val toolsList = listOf(
        ExternalTool(
            id = "meta_ai",
            name = "Meta AI",
            category = "Image & Assistant",
            categoryHi = "इमेज एवं असिस्टेंट",
            officialUrl = "https://www.meta.ai",
            shortSummaryEn = "Meta's official AI assistant accessible via web and inside WhatsApp / Instagram for prompt-based image creation and style changes.",
            shortSummaryHi = "मेटा का आधिकारिक AI असिस्टेंट जो वेब, व्हाट्सएप और इंस्टाग्राम में प्रॉम्प्ट से इमेज जनरेट व एडिट करने में मदद करता है।",
            supportedWorkflowsEn = listOf(
                "Text-to-Image creation",
                "Re-imagine photo styles",
                "Direct Instagram chat prompt rendering"
            ),
            supportedWorkflowsHi = listOf(
                "टेक्स्ट से इमेज निर्माण",
                "फोटो स्टाइल बदलना",
                "इंस्टाग्राम चैट में डायरेक्ट प्रॉम्प्ट"
            ),
            pricingStatus = "Free tier available (requires Meta / FB account).",
            pricingStatusHi = "फ्री टियर उपलब्ध (मेटा/फेसबुक अकाउंट आवश्यक)।",
            regionalNoticeEn = "Feature availability may vary depending on regional Meta rollouts.",
            regionalNoticeHi = "सुविधा की उपलब्धता आपके क्षेत्र और मेटा रोलआउट पर निर्भर कर सकती है।"
        ),
        ExternalTool(
            id = "instagram_edits",
            name = "Instagram Edits & Reels Studio",
            category = "Social Video & Audio",
            categoryHi = "सोशल वीडियो व ऑडियो",
            officialUrl = "https://www.instagram.com",
            shortSummaryEn = "Instagram's in-app creation suite featuring speed ramping, AI sticker cutouts, beat-synced clips, and trending audio libraries.",
            shortSummaryHi = "इंस्टाग्राम का इन-ऐप वीडियो एडिटर जिसमें स्पीड रैंपिंग, AI स्टीकर कटआउट, बीट सिंक क्लिप्स और ट्रेंडिंग ऑडियो लाइब्रेरी शामिल हैं।",
            supportedWorkflowsEn = listOf(
                "9:16 vertical reels formatting",
                "Trending audio synchronization",
                "AI sticker cutout from camera roll"
            ),
            supportedWorkflowsHi = listOf(
                "9:16 वर्टिकल रील्स फॉर्मेटिंग",
                "ट्रेंडिंग ऑडियो सिंक",
                "गैलरी से AI कटआउट स्टीकर"
            ),
            pricingStatus = "Free (Standard Instagram app).",
            pricingStatusHi = "मुफ्त (स्टैंडर्ड इंस्टाग्राम ऐप)।",
            regionalNoticeEn = "Some licensed music tracks and AI effect stickers are limited in specific regions.",
            regionalNoticeHi = "कुछ म्यूजिक ट्रैक्स और AI प्रभाव केवल चुनिंदा क्षेत्रों में उपलब्ध हैं।"
        ),
        ExternalTool(
            id = "capcut",
            name = "CapCut Video Editor",
            category = "Reels & Video Effects",
            categoryHi = "रील्स एवं वीडियो इफेक्ट्स",
            officialUrl = "https://www.capcut.com",
            shortSummaryEn = "Popular mobile and desktop video editing suite with ready-made Reels templates, 3D zoom effects, and keyframe motion.",
            shortSummaryHi = "लोकप्रिय मोबाइल और डेस्कटॉप वीडियो एडिटर जिसमें रेडी-मेड रील्स टेम्प्लेट, 3D ज़ूम और मोशन कीफ्रेम शामिल हैं।",
            supportedWorkflowsEn = listOf(
                "One-click viral photo-to-video templates",
                "3D smooth camera push-ins",
                "Auto-captions and beat detection"
            ),
            supportedWorkflowsHi = listOf(
                "वन-क्लिक फोटो-टू-वीडियो टेम्प्लेट्स",
                "3D स्मूथ कैमरा पुश-इन इफेक्ट्स",
                "ऑटो कैप्शन और बीट डिटेक्शन"
            ),
            pricingStatus = "Free basic tools with CapCut Pro subscription options.",
            pricingStatusHi = "फ्री बेसिक फीचर्स, साथ में CapCut Pro का विकल्प।",
            regionalNoticeEn = "App store availability varies across countries; check local app stores.",
            regionalNoticeHi = "ऐप स्टोर में उपलब्धता देश के नियमों के अनुसार अलग हो सकती है।"
        ),
        ExternalTool(
            id = "bing_copilot",
            name = "Microsoft Copilot / Designer",
            category = "High Detail Image Creation",
            categoryHi = "हाई डिटेल इमेज निर्माण",
            officialUrl = "https://copilot.microsoft.com/images/create",
            shortSummaryEn = "Microsoft's creative image generator powered by DALL-E models, popular for 3D wings, glowing name typography, and festive portraits.",
            shortSummaryHi = "माइक्रोसॉफ्ट का इमेज जनरेटर जो DALL-E तकनीक पर आधारित है। 3D विंग्स, ग्लोइंग नाम और रॉयल पोर्ट्रेट्स के लिए बेहद लोकप्रिय।",
            supportedWorkflowsEn = listOf(
                "Detailed textual portraits with custom names",
                "Photorealistic studio lighting",
                "Festive and traditional 3D artwork"
            ),
            supportedWorkflowsHi = listOf(
                "कस्टम नाम के साथ विस्तृत 3D पोर्ट्रेट",
                "फोटोरियलिस्टिक स्टूडियो लाइटिंग",
                "त्यौहार और सांस्कृतिक 3D आर्ट"
            ),
            pricingStatus = "Free daily Boosts with Microsoft account.",
            pricingStatusHi = "माइक्रोसॉफ्ट अकाउंट के साथ दैनिक फ्री बूस्ट्स।",
            regionalNoticeEn = "Requires a personal Microsoft account for generation credits.",
            regionalNoticeHi = "इमेज जनरेशन के लिए पर्सनल माइक्रोसॉफ्ट अकाउंट आवश्यक है।"
        ),
        ExternalTool(
            id = "leonardo_ai",
            name = "Leonardo.Ai",
            category = "Cinematic & Concept Art",
            categoryHi = "सिनेमैटिक व कॉन्सेप्ट आर्ट",
            officialUrl = "https://leonardo.ai",
            shortSummaryEn = "Production-quality visual asset platform with fine-tuned models for cinematic photography, fantasy art, and character consistency.",
            shortSummaryHi = "हाई-क्वालिटी विजुअल प्लेटफॉर्म जिसमें सिनेमैटिक फोटोग्राफी, फैंटेसी आर्ट और सुसंगत कैरेक्टर मॉडल्स शामिल हैं।",
            supportedWorkflowsEn = listOf(
                "Cinematic camera depth and lens emulation",
                "Negative prompting and photoreal styles",
                "Canvas editor for inpainting and outpainting"
            ),
            supportedWorkflowsHi = listOf(
                "सिनेमैटिक कैमरा डेप्थ और लेंस इफ़ेक्ट",
                "नेगेटिव प्रॉम्प्ट्स और रियलिस्टिक स्टाइल",
                "कैनवास इनपेंटिंग और एडिट टूल"
            ),
            pricingStatus = "Free daily quota (150 tokens) with premium upgrade plans.",
            pricingStatusHi = "रोजाना 150 फ्री टोकन्स, प्रीमियम अपग्रेड के विकल्प के साथ।",
            regionalNoticeEn = "Web browser interface works internationally.",
            regionalNoticeHi = "वेब ब्राउज़र इंटरफ़ेस अंतरराष्ट्रीय स्तर पर काम करता है।"
        ),
        ExternalTool(
            id = "canva",
            name = "Canva Visual Suite",
            category = "Thumbnail & Story Layouts",
            categoryHi = "थंबनेल व स्टोरी लेआउट्स",
            officialUrl = "https://www.canva.com",
            shortSummaryEn = "Design platform for assembling Instagram story layouts, carousel slides, text overlays, and photo enhancement.",
            shortSummaryHi = "इंस्टाग्राम स्टोरी लेआउट्स, कैरोसेल पोस्ट्स, टेक्स्ट ओवरले और फोटो सुधार के लिए सर्वोत्तम डिज़ाइन प्लेटफॉर्म।",
            supportedWorkflowsEn = listOf(
                "9:16 Reel cover and story templates",
                "Background remover and shadow adjustment",
                "Typography and sticker graphics"
            ),
            supportedWorkflowsHi = listOf(
                "9:16 रील्स कवर और स्टोरी टेम्प्लेट",
                "बैकग्राउंड रिमूवर और शैडो एडजस्टमेंट",
                "स्टाइलिश टाइपोग्राफी और स्टिकर"
            ),
            pricingStatus = "Free tier available; Canva Pro includes premium assets.",
            pricingStatusHi = "फ्री टियर उपलब्ध; प्रो में प्रीमियम एसेट्स मिलते हैं।",
            regionalNoticeEn = "Available worldwide on Web, iOS, and Android.",
            regionalNoticeHi = "वेब, आईओएस और एंड्रॉइड पर वैश्विक स्तर पर उपलब्ध।"
        ),
        ExternalTool(
            id = "runway_ml",
            name = "Runway Gen-3",
            category = "AI Video Generation",
            categoryHi = "AI वीडियो जनरेशन",
            officialUrl = "https://runwayml.com",
            shortSummaryEn = "Pioneering creative video research platform providing motion brush, camera control, and photo-to-video animation.",
            shortSummaryHi = "रिसर्च-ग्रेड वीडियो प्लेटफॉर्म जिसमें मोशन ब्रश, कैमरा कंट्रोल और फोटो-टू-वीडियो एनीमेशन की सुविधा है।",
            supportedWorkflowsEn = listOf(
                "Still image animation with directional motion",
                "Cinematic slow-motion zoom and pan",
                "Atmospheric smoke and water physics"
            ),
            supportedWorkflowsHi = listOf(
                "फोटो को मोशन में बदलना",
                "सिनेमैटिक स्लो-मोशन ज़ूम व पैन",
                "धुआं और पानी का प्राकृतिक मोशन"
            ),
            pricingStatus = "Free starter trial credits; paid subscription for expanded generation.",
            pricingStatusHi = "शुरुआती फ्री ट्रायल क्रेडिट्स; अधिक के लिए सशुल्क प्लान।",
            regionalNoticeEn = "Video rendering speeds depend on queue load and subscription tier.",
            regionalNoticeHi = "वीडियो रेंडरिंग का समय सर्वर लोड पर निर्भर करता है।"
        )
    )

    private val samplePrompts = listOf(
        PromptTutorial(
            id = "trend_royal_wings_throne",
            titleEn = "Royal Golden Throne with Neon Angel Wings",
            titleHi = "रॉयल गोल्डन सिंहासन और नियॉन एंजल विंग्स",
            category = "Royal & 3D Photo Styles",
            categoryHi = "रॉयल और 3D फोटो स्टाइल्स",
            contentType = ContentType.PHOTO,
            difficulty = Difficulty.BEGINNER,
            requiredInput = RequiredInput.TEXT_ONLY,
            trendStatus = TrendStatus.HOT_TREND,
            lastVerifiedDate = "Verified October 2026",
            suggestedAspectRatio = "9:16 (Instagram Reel / Story)",
            promptText = "A realistic 3D portrait of a stylish young Indian man in modern black velvet ethnic sherwani with gold embroidery, sitting comfortably on a majestic obsidian and gold royal throne. Massive radiant neon cyan and soft purple glowing angel wings emerge behind the throne. In the background, dramatic royal palace hall with moody atmospheric mist, volumetric golden god-rays, 8k resolution, ultra-detailed textures, cinematic Unreal Engine 5 render, award winning photography.",
            promptExplanationEn = "This prompt crafts a regal, cinematic aesthetic by blending luxury ethnic attire with mystical neon wings and dramatic palace lighting.",
            promptExplanationHi = "यह प्रॉम्प्ट शाही शेरवानी और राजसी सिंहासन के साथ नियॉन विंग्स और नाटकीय पैलेस लाइटिंग को जोड़कर एक आकर्षक फोटो बनाता है।",
            visualEffectEn = "Dark moody throne room with high-contrast glowing angel wings and sharp golden embroidery reflections.",
            visualEffectHi = "अंधेरे शाही हॉल में चमकते हुए नियॉन विंग्स और सुनहरे कपड़ों की रिफ्लेक्शन का शानदार कंट्रास्ट प्रभाव।",
            supportedTools = listOf(
                ToolReference("Microsoft Copilot / Designer", "https://copilot.microsoft.com/images/create", "Free with account"),
                ToolReference("Meta AI", "https://www.meta.ai", "Free tier"),
                ToolReference("Leonardo.Ai", "https://leonardo.ai", "Free daily credits")
            ),
            steps = listOf(
                TutorialStep(
                    1,
                    "Copy the Prompt",
                    "प्रॉम्प्ट कॉपी करें",
                    "Click the 'Copy Prompt' button below. You can customize the outfit color, hair style, or replace 'Indian man' with 'young woman in royal lehenga'.",
                    "नीचे दिए गए 'Copy Prompt' बटन पर टैप करें। आप अपनी पसंद के अनुसार 'Indian man' की जगह 'young woman in royal lehenga' या शेरवानी का रंग बदल सकते हैं।"
                ),
                TutorialStep(
                    2,
                    "Open AI Tool",
                    "AI टूल खोलें",
                    "Open Microsoft Copilot Image Creator or Meta AI using the verified link provided.",
                    "वेरिफाइड लिंक का उपयोग करके Microsoft Copilot या Meta AI खोलें।"
                ),
                TutorialStep(
                    3,
                    "Paste & Generate",
                    "प्रॉम्प्ट पेस्ट करें और बनाएं",
                    "Paste the copied prompt into the input bar and click Create. Wait 15-30 seconds for the variations.",
                    "इनपुट बॉक्स में प्रॉम्प्ट पेस्ट करें और 'Create' पर टैप करें। 15-30 सेकंड प्रतीक्षा करें।"
                ),
                TutorialStep(
                    4,
                    "Select & Download Full Resolution",
                    "सर्वश्रेष्ठ फोटो चुनें व डाउनलोड करें",
                    "Pick the clearest face and highest detail result. Download in maximum resolution.",
                    "सबसे साफ चेहरे और बेहतरीन डिटेल वाली फोटो चुनकर फुल रिज़ॉल्यूशन में सेव करें।"
                )
            ),
            proTipsEn = listOf(
                "Add your own name in the prompt (e.g., 'with the name [YOUR_NAME] elegantly engraved in glowing neon script on the throne').",
                "Ensure lighting terms like 'volumetric god-rays' are retained for depth.",
                "For male or female subjects, specify footwear like 'clean white sneakers' or 'traditional mojari'."
            ),
            proTipsHi = listOf(
                "प्रॉम्प्ट में अपना नाम जोड़ें: 'with the name [आपका नाम] glowing in gold on the back wall'.",
                "डेप्थ और चमक के लिए 'volumetric god-rays' शब्द को न हटाएं।",
                "पुरुष या महिला किरदार के अनुसार परिधान को कस्टमाइज़ करें।"
            ),
            commonMistakesEn = listOf(
                "Writing too many conflicting outfit descriptions which confuses the AI.",
                "Selecting lower-resolution preview screenshots instead of clicking download."
            ),
            commonMistakesHi = listOf(
                "बहुत सारे अलग-अलग कपड़ों का वर्णन करना जिससे AI भ्रमित हो जाता है।",
                "डाउनलोड करने के बजाय स्क्रीनशॉट लेना, जिससे क्वालिटी कम हो जाती है।"
            ),
            instagramExportGuideEn = "Crop or export to 9:16 (1080x1920 pixels). In Instagram Reels, pair with trending royal instrumental or slow-reverb soundtrack, add a subtle slow zoom-in transition.",
            instagramExportGuideHi = "9:16 (1080x1920 पिक्सल) में तैयार करें। इंस्टाग्राम पर रॉयल स्लो-रेवर्व बीजीएम के साथ अपलोड करें और हल्का स्लो ज़ूम इफ़ेक्ट दें।",
            hashtags = listOf("#AIPrompts", "#RoyalPortrait", "#TrendingReels", "#AIEditing", "#HRAIStudio", "#InstaTrends2026", "#ViralReels"),
            captionsEn = listOf(
                "Sitting on my throne of dreams. ✨ Generated with HR AI Studio prompt.",
                "Royalty isn't a crown, it's an aura. 👑 Try this trending prompt!"
            ),
            captionsHi = listOf(
                "सपनों के सिंहासन पर एक नया अंदाज़! ✨ HR AI Studio प्रॉम्प्ट से तैयार।",
                "रॉयल लुक, ट्रेंडिंग वाइब्स। 👑 खुद का अवतार बनाएं!"
            ),
            primaryColorHex = 0xFF38BDF8,
            secondaryColorHex = 0xFFA855F7,
            iconName = "throne"
        ),

        PromptTutorial(
            id = "trend_photo_to_video_smoke",
            titleEn = "Cinematic Photo-to-Video Smoke & Zoom Push",
            titleHi = "सिनेमैटिक फोटो-टू-वीडियो स्मोक व ज़ूम पुश",
            category = "Photo to Video Tutorials",
            categoryHi = "फोटो से वीडियो ट्यूटोरियल्स",
            contentType = ContentType.VIDEO,
            difficulty = Difficulty.INTERMEDIATE,
            requiredInput = RequiredInput.PHOTO_UPLOAD,
            trendStatus = TrendStatus.REEL_VIRAL,
            lastVerifiedDate = "Verified October 2026",
            suggestedAspectRatio = "9:16 (Vertical Reel)",
            promptText = "Continuous smooth dolly zoom pushing forward toward the subject, dramatic atmospheric dense blue and violet smoke swirling across the floor, subtle hair movement in gentle breeze, cinematic lighting, 60fps fluid motion, hyper-realistic physics.",
            promptExplanationEn = "This video-prompt instruction is meant for image-to-video tools (like Runway or CapCut motion) to transform a still portrait into a dynamic moving reel.",
            promptExplanationHi = "यह वीडियो-प्रॉम्प्ट किसी भी साधारण फोटो को घूमते हुए धुएं, धीमी हवा और आगे बढ़ते कैमरे के साथ रियलिस्टिक वीडियो में बदलने के लिए है।",
            visualEffectEn = "Gentle camera push-in with realistic curling smoke mist and natural eye/hair breathing motion.",
            visualEffectHi = "कैमरा धीरे-धीरे आगे बढ़ता है और पैरों के पास आकर्षक नियॉन धुआं लहराता है।",
            supportedTools = listOf(
                ToolReference("RunwayML Gen-3", "https://runwayml.com", "Trial credits available"),
                ToolReference("CapCut", "https://www.capcut.com", "Free 3D Zoom templates"),
                ToolReference("Instagram Edits", "https://www.instagram.com", "Free built-in editor")
            ),
            steps = listOf(
                TutorialStep(
                    1,
                    "Prepare High Resolution Portrait",
                    "हाई रिज़ॉल्यूशन फोटो तैयार करें",
                    "Take a high-quality centered portrait or use one generated from our Photo Prompts category.",
                    "एक साफ और हाई क्वालिटी वाली फोटो चुनें जिसमें व्यक्ति सेंटर में हो।"
                ),
                TutorialStep(
                    2,
                    "Upload to Motion Tool",
                    "मोशन टूल में फोटो अपलोड करें",
                    "Open RunwayML (or CapCut 3D Zoom template) and upload your portrait.",
                    "RunwayML या CapCut में जाकर फोटो अपलोड करें।"
                ),
                TutorialStep(
                    3,
                    "Set Camera Speed & Motion Brush",
                    "कैमरा स्पीड और मोशन सेट करें",
                    "Apply 'Forward Dolly' speed at 2.0x, and paint the smoke area with the motion brush moving upward.",
                    "कैमरा मोशन को 'Forward Zoom' पर रखें और स्मोक वाले हिस्से पर ऊपर की दिशा का मोशन ब्रश लगाएं।"
                ),
                TutorialStep(
                    4,
                    "Render & Beat-Sync in Instagram",
                    "रेंडर करें और इंस्टाग्राम पर बीट-सिंक करें",
                    "Export the 4-second clip at 1080p. In Instagram Reels, align the camera push with the beat drop.",
                    "वीडियो डाउनलोड करें और इंस्टाग्राम रील्स में बीट ड्रॉप पर इस क्लिप को सिंक करें।"
                )
            ),
            proTipsEn = listOf(
                "Keep camera motion speed low (1.5 - 2.5) to avoid facial warping.",
                "Add sound design: deep bass whoosh sound on the camera push-in."
            ),
            proTipsHi = listOf(
                "कैमरा स्पीड ज्यादा तेज न रखें ताकि चेहरा विकृत न हो।",
                "रील्स में कैमरा आगे बढ़ने के साथ वूश (Whoosh) साउंड इफ़ेक्ट जोड़ें।"
            ),
            commonMistakesEn = listOf(
                "Uploading blurry or low-light photos which cause motion artifacts.",
                "Using too many conflicting directions on the motion brush."
            ),
            commonMistakesHi = listOf(
                "कम रोशनी वाली धुंधली फोटो डालना, जिससे वीडियो में ग्लिच आने लगते हैं।",
                "एक साथ कई विपरीत दिशाओं में मोशन सेट करना।"
            ),
            instagramExportGuideEn = "Export at 1080x1920, 60fps H.264 format. Ensure Instagram 'Upload at highest quality' setting is enabled in Data Usage.",
            instagramExportGuideHi = "1080x1920, 60fps में एक्सपोर्ट करें। इंस्टाग्राम सेटिंग्स में 'Upload at highest quality' चालू रखें।",
            hashtags = listOf("#PhotoToVideo", "#CapCutTemplate", "#RunwayAI", "#ReelsTutorial", "#HRAIStudio", "#VideoEditing"),
            captionsEn = listOf(
                "From a still frame to cinematic motion in seconds! 🎬 Tutorial via HR AI Studio.",
                "When the beat drops and the camera pushes in... 🔥 Check the prompt!"
            ),
            captionsHi = listOf(
                "सिर्फ एक फोटो से 4 सेकंड का सिनेमैटिक जादू! 🎬 HR AI Studio ट्यूटोरियल।",
                "बीट ड्रॉप के साथ स्मोक मोशन रील्स। 🔥 अभी आज़माएं!"
            ),
            primaryColorHex = 0xFF818CF8,
            secondaryColorHex = 0xFFEC4899,
            iconName = "videocam"
        ),

        PromptTutorial(
            id = "trend_cyberpunk_neon_rain",
            titleEn = "Cyberpunk Rain City Street Portrait",
            titleHi = "साइबरपंक नियॉन रेन सिटी स्ट्रीट पोर्ट्रेट",
            category = "Cinematic Reels",
            categoryHi = "सिनेमैटिक रील्स",
            contentType = ContentType.PHOTO,
            difficulty = Difficulty.BEGINNER,
            requiredInput = RequiredInput.TEXT_ONLY,
            trendStatus = TrendStatus.HOT_TREND,
            lastVerifiedDate = "Verified October 2026",
            suggestedAspectRatio = "9:16 (Story / Reel Cover)",
            promptText = "A high-fashion cinematic portrait of a handsome person standing under a clear transparent umbrella in a neon-lit futuristic Tokyo/Mumbai street at midnight during heavy rain. Wet asphalt reflecting vibrant magenta, cyan, and amber neon billboards. Rain droplets glistening on the umbrella and leather jacket, shallow depth of field, 85mm f/1.4 lens bokeh, cinematic teal and orange color grading, masterwork, 8k resolution.",
            promptExplanationEn = "Captures the moody rain aesthetic with saturated reflections and high-end cinematic lens characteristics.",
            promptExplanationHi = "बारिश में भीगी सड़क, पारदर्शी छाता और चमकीले नियॉन विज्ञापनों के रिफ्लेक्शन के साथ एक हाई-एंड सिनेमैटिक पोर्ट्रेट।",
            visualEffectEn = "Glistening rain droplets on umbrella, bokeh street lights, and dramatic neon side rim lighting.",
            visualEffectHi = "छाते पर पानी की बूंदें, पीछे नियॉन बुके लाइट्स और चेहरे पर दोहरी रंगीन लाइटिंग।",
            supportedTools = listOf(
                ToolReference("Meta AI", "https://www.meta.ai", "Free tier"),
                ToolReference("Leonardo.Ai", "https://leonardo.ai", "Free tier"),
                ToolReference("Microsoft Copilot", "https://copilot.microsoft.com/images/create", "Free tier")
            ),
            steps = listOf(
                TutorialStep(1, "Copy the Prompt", "प्रॉम्प्ट कॉपी करें", "Tap Copy Prompt. Replace 'Tokyo/Mumbai' with your preferred city backdrop if desired.", "प्रॉम्प्ट कॉपी करें। अपनी पसंद के शहर का नाम भी डाल सकते हैं।"),
                TutorialStep(2, "Generate with Tool", "टूल में बनाएं", "Paste into Meta AI or Copilot. Ask for 9:16 portrait orientation.", "टूल में पेस्ट करें और 9:16 पोर्ट्रेट अनुपात में रेंडर करें।"),
                TutorialStep(3, "Fine Tune Details", "डिटेल्स एडजस्ट करें", "If the face needs refinement, add 'sharp facial focus, realistic human skin pores'.", "अगर चेहरा ज्यादा शार्प चाहिए तो 'sharp focus on face' जोड़ें।")
            ),
            proTipsEn = listOf(
                "Mention '85mm f/1.4 lens' to get smooth background blur.",
                "Specify clothing texture like 'matte black motorcycle jacket' for realism."
            ),
            proTipsHi = listOf(
                "बैकग्राउंड ब्लर के लिए '85mm f/1.4 lens' शब्द का प्रयोग करें।",
                "कपड़ों में 'matte leather jacket' लिखने से रियलिस्टिक टेक्सचर आता है।"
            ),
            commonMistakesEn = listOf("Forgetting to specify the umbrella transparency, causing an opaque umbrella to block the light."),
            commonMistakesHi = listOf("पारदर्शी छाता लिखना भूल जाना, जिससे छाता रोशनी को रोक देता है।"),
            instagramExportGuideEn = "Best posted as a Carousel cover or with an animated rain filter overlay on Instagram Stories.",
            instagramExportGuideHi = "इंस्टाग्राम रील्स या स्टोरी कवर के लिए उपयुक्त। बारिश वाले साउंड इफ़ेक्ट के साथ पोस्ट करें।",
            hashtags = listOf("#CyberpunkVibes", "#NeonRain", "#CinematicPortrait", "#AIPrompting", "#HRAIStudio"),
            captionsEn = listOf("Lost in the neon rain. 🌧️ Neon city portrait prompt.", "Night city whispers in neon lights. ⚡"),
            captionsHi = listOf("नियॉन बारिश और शहर की रात। 🌧️ नया सिनेमैटिक प्रॉम्प्ट।", "साइबरपंक वाइब्स। ⚡ HR AI Studio से तैयार।"),
            primaryColorHex = 0xFF22D3EE,
            secondaryColorHex = 0xFF3B82F6,
            iconName = "water_drop"
        ),

        PromptTutorial(
            id = "trend_3d_pixar_caricature",
            titleEn = "3D Pixar Style Cute Caricature Avatar",
            titleHi = "3D पिक्सर स्टाइल क्यूट कैरिकेचर अवतार",
            category = "3D Character and Cartoon Styles",
            categoryHi = "3D कैरेक्टर और कार्टून स्टाइल्स",
            contentType = ContentType.PHOTO,
            difficulty = Difficulty.BEGINNER,
            requiredInput = RequiredInput.TEXT_ONLY,
            trendStatus = TrendStatus.HOT_TREND,
            lastVerifiedDate = "Verified October 2026",
            suggestedAspectRatio = "1:1 (Profile Picture / Post)",
            promptText = "A cute 3D Pixar Disney animation style digital illustration of a smiling cheerful Indian boy wearing trendy oversized hoodie, round spectacles, and wireless headphones, sitting with a fluffy golden retriever puppy. Warm cozy ambient lighting, pastel soft clay textures, smooth subsurface scattering, detailed fur, vibrant playful colors, 3D render by Disney animation studios.",
            promptExplanationEn = "Creates endearing, family-friendly cartoon avatars ideal for profile pictures and engaging story posts.",
            promptExplanationHi = "डिज़्नी-पिक्सर स्टाइल में गोल चश्मे, हुडी और प्यारे पपी के साथ एक मुस्कराता हुआ 3D कार्टून कैरेक्टर।",
            visualEffectEn = "Warm volumetric lighting, soft clay rendering, and big expressive Disney eyes.",
            visualEffectHi = "मुलायम क्ले टेक्सचर, बड़ी भावुक आंखें और गर्म बैकग्राउंड लाइटिंग।",
            supportedTools = listOf(
                ToolReference("Microsoft Copilot", "https://copilot.microsoft.com/images/create", "Free tier"),
                ToolReference("Meta AI", "https://www.meta.ai", "Free tier"),
                ToolReference("Canva", "https://www.canva.com", "Free tool")
            ),
            steps = listOf(
                TutorialStep(1, "Copy & Edit Name/Features", "प्रॉम्प्ट कॉपी और कस्टमाइज़ करें", "Copy prompt and change hair style or pet type (e.g. fluffy cat, husky).", "प्रॉम्प्ट कॉपी करें और हुडी का रंग या पेट (पपी/कैट) बदलें।"),
                TutorialStep(2, "Generate 4 Options", "4 विकल्प बनाएं", "Generate using Bing Image Creator or Meta AI.", "टूल में रन करके 4 विकल्प जनरेट करें।"),
                TutorialStep(3, "Apply as DP", "डीपी के रूप में उपयोग करें", "Crop 1:1 square for Instagram DP or WhatsApp profile.", "1:1 चौकोर अनुपात में प्रोफाइल पिक्चर के रूप में लगाएं।")
            ),
            proTipsEn = listOf("Specify 'subsurface scattering' to make the skin look warm and soft like real animated characters."),
            proTipsHi = listOf("स्किन को सजीव और कोमल दिखाने के लिए 'subsurface scattering' शब्द का प्रयोग करें।"),
            commonMistakesEn = listOf("Using the word 'realistic' alongside 'cartoon', which can result in uncanny renders."),
            commonMistakesHi = listOf("'Cartoon' के साथ 'hyper realistic' लिखना, जिससे चेहरा अजीब दिखने लगता है।"),
            instagramExportGuideEn = "Square 1080x1080 resolution. Perfect for viral 'Cartoonize Yourself' carousel trends.",
            instagramExportGuideHi = "1080x1080 पिक्सल चौकोर साइज। इंस्टाग्राम प्रोफाइल या 'Cartoon Avatar' ट्रेंड के लिए बेहतरीन।",
            hashtags = listOf("#PixarStyle", "#3DAvatar", "#CartoonTrend", "#DisneyAI", "#HRAIStudio"),
            captionsEn = listOf("If I were in an animated movie! 🍿✨ Created with HR AI Studio.", "My animated self with my best buddy 🐶💫"),
            captionsHi = listOf("अगर मैं किसी 3D कार्टून फिल्म में होता! 🍿✨ HR AI Studio से तैयार।", "मेरा नया 3D पिक्सर अवतार! 🐶💫"),
            primaryColorHex = 0xFFF59E0B,
            secondaryColorHex = 0xFFEF4444,
            iconName = "face"
        ),

        PromptTutorial(
            id = "trend_ai_photo_editing_moody_teal",
            titleEn = "Moody Dark Teal & Orange Color Grading Guide",
            titleHi = "मूडी डार्क टील एवं ऑरेंज कलर ग्रेडिंग गाइड",
            category = "AI Photo Editing",
            categoryHi = "AI फोटो एडिटिंग",
            contentType = ContentType.EDITING,
            difficulty = Difficulty.INTERMEDIATE,
            requiredInput = RequiredInput.PHOTO_UPLOAD,
            trendStatus = TrendStatus.CLASSIC_STYLE,
            lastVerifiedDate = "Verified October 2026",
            suggestedAspectRatio = "4:5 (Instagram Portrait Feed)",
            promptText = "Enhance existing photo: Deep cinematic shadows, teal cyan tones in ambient shadow regions, warm amber orange glow on facial skin tones, faded crushed blacks, 35mm subtle grain, high clarity on clothing textures, remove background clutter, studio rim light.",
            promptExplanationEn = "Step-by-step editing prompt and slider workflow to give casual mobile photos an editorial magazine look.",
            promptExplanationHi = "मोबाइल से खींची गई साधारण फोटो को मैगज़ीन जैसी टील और ऑरेंज सिनेमैटिक टोन देने की संपूर्ण विधि।",
            visualEffectEn = "Complementary color contrast: deep teal backgrounds with warm golden skin highlights.",
            visualEffectHi = "बैकग्राउंड में ठंडा टील रंग और चेहरे पर गर्म सुनहरी रोशनी का आकर्षक संयोजन।",
            supportedTools = listOf(
                ToolReference("Meta AI", "https://www.meta.ai", "Free edit mode"),
                ToolReference("Canva Photo Enhancer", "https://www.canva.com", "Free tools"),
                ToolReference("Instagram Filters", "https://www.instagram.com", "In-app sliders")
            ),
            steps = listOf(
                TutorialStep(1, "Adjust Shadows & Highlights", "शैडो और हाइलाइट्स सेट करें", "In your photo editor, lower Shadows to -20 and lower Highlights to -15 to retain details.", "एडिटर में शैडो को -20 और हाइलाइट्स को -15 करें ताकि डिटेल्स न खोएं।"),
                TutorialStep(2, "Color Split Toning", "कलर स्प्लिट टोनिंग", "Push Shadows toward Teal/Cyan (Hue 190) and Highlights toward Warm Amber (Hue 35).", "शैडोज़ में हल्का टील (ब्लू) और हाइलाइट्स में वॉर्म अंबर (ऑरेंज) रंग भरें।"),
                TutorialStep(3, "Add Film Grain", "हल्का फिल्म ग्रेन जोड़ें", "Add 8-12% fine grain for texture and reduce digital sharpness.", "8-12% हल्का फिल्म ग्रेन जोड़ें जिससे फोटो प्राकृतिक दिखे।")
            ),
            proTipsEn = listOf("Don't over-saturate skin tones. Keep orange saturation between +5 and +12."),
            proTipsHi = listOf("स्किन टोन को ज्यादा संकुचित या पीला न करें; ऑरेंज सैचुरेशन हल्का रखें।"),
            commonMistakesEn = listOf("Crushing blacks too aggressively, losing hair and clothing detail."),
            commonMistakesHi = listOf("काले रंग को बहुत ज्यादा डार्क कर देना जिससे बालों की डिटेल गायब हो जाती है।"),
            instagramExportGuideEn = "Export in 4:5 ratio (1080x1350px) which fills maximum vertical space in the Instagram home feed without cropping.",
            instagramExportGuideHi = "4:5 अनुपात (1080x1350 पिक्सल) में एक्सपोर्ट करें जो इंस्टाग्राम फीड में सबसे ज्यादा जगह घेरता है।",
            hashtags = listOf("#TealAndOrange", "#PhotoEditing", "#ColorGrading", "#LightroomVibes", "#HRAIStudio"),
            captionsEn = listOf("That cinematic street feeling. 🎞️ Color graded with HR AI Studio guide.", "Cinematic tones make every frame speak. 📸"),
            captionsHi = listOf("सिनेमैटिक टील और ऑरेंज जादू! 🎞️ HR AI Studio कलर गाइड।", "हर फ्रेम में कहानी है। 📸"),
            primaryColorHex = 0xFF0D9488,
            secondaryColorHex = 0xFFF97316,
            iconName = "tune"
        ),

        PromptTutorial(
            id = "trend_festival_colors_diwali_holi",
            titleEn = "Festive Radiance: Golden Diya & Floating Particles",
            titleHi = "फेस्टिव रेडिएंस: गोल्डन दीया और फ्लोटिंग पार्टिकल्स",
            category = "Festival Photo Styles",
            categoryHi = "त्यौहार फोटो स्टाइल्स",
            contentType = ContentType.PHOTO,
            difficulty = Difficulty.BEGINNER,
            requiredInput = RequiredInput.TEXT_ONLY,
            trendStatus = TrendStatus.HOT_TREND,
            lastVerifiedDate = "Verified October 2026",
            suggestedAspectRatio = "9:16 (Story / Reel)",
            promptText = "A breathtaking festive portrait of a joyful Indian person in traditional silk kurta/saree with intricate zardozi embroidery, holding an ornate glowing brass diya lamp between both hands. Soft golden sparks, luminous bokeh lights, and delicate marigold petals floating gently in the air. Traditional palace courtyard backdrop under a deep starlit twilight sky, cinematic depth of field, warm candlelight glow on face, masterwork 8k.",
            promptExplanationEn = "Celebrates traditional festivals with warm diya illumination, glowing marigold petals, and luxurious silk textures.",
            promptExplanationHi = "हाथों में चमकता पीतल का दीया, उड़ती गेंदे की पंखुड़ियाँ और रेशमी पारंपरिक पोशाक के साथ मनमोहक त्यौहार का माहौल।",
            visualEffectEn = "Warm 2400K candlelight illuminating face, floating luminous sparks, and deep royal night sky.",
            visualEffectHi = "चेहरे पर दीए की गर्म सुनहरी चमक और बैकग्राउंड में जगमगाती लाइटें।",
            supportedTools = listOf(
                ToolReference("Microsoft Copilot", "https://copilot.microsoft.com/images/create", "Free"),
                ToolReference("Meta AI", "https://www.meta.ai", "Free"),
                ToolReference("Leonardo.Ai", "https://leonardo.ai", "Free credits")
            ),
            steps = listOf(
                TutorialStep(1, "Copy & Customize", "कॉपी और नाम बदलें", "Copy prompt. You can change 'kurta' or 'saree' and choose colors like emerald green or crimson red.", "प्रॉम्प्ट कॉपी करें। आप कुर्ते या साड़ी का रंग लाल या हरा चुन सकते हैं।"),
                TutorialStep(2, "Generate with Copilot", "Copilot में जनरेट करें", "Generate in Microsoft Copilot for optimal candle reflections and particle effects.", "Copilot में पेस्ट करें जहां लाइटिंग और पार्टिकल्स सबसे बेहतरीन बनते हैं।"),
                TutorialStep(3, "Post on Festival Days", "त्यौहार पर पोस्ट करें", "Add festive greeting text in Canva before posting.", "कैनवा में 'शुभकामनाएं' टेक्स्ट जोड़कर इंस्टाग्राम पर शेयर करें।")
            ),
            proTipsEn = listOf("Include 'delicate marigold petals floating' to give three-dimensional depth to the foreground."),
            proTipsHi = listOf("आगे की तरफ डेप्थ देने के लिए 'floating marigold petals' का उपयोग करें।"),
            commonMistakesEn = listOf("Adding too many bright background lights that overpower the warm face illumination."),
            commonMistakesHi = listOf("बैकग्राउंड में बहुत तेज लाइट रखना जिससे दीए की मुख्य चमक फीकी पड़ जाए।"),
            instagramExportGuideEn = "Vertical 9:16. Pair with traditional sitar or soothing devotional flute music on Instagram audio.",
            instagramExportGuideHi = "9:16 वर्टिकल साइज। मधुर बांसुरी या सितार म्यूजिक के साथ स्टोरी या रील में शेयर करें।",
            hashtags = listOf("#FestiveVibes", "#DiwaliReels", "#TraditionalAesthetics", "#HRAIStudio", "#IndianFestivals"),
            captionsEn = listOf("May your days sparkle with joy and prosperity. ✨ Happy festive season!", "Carrying the light within. 🪔 Prompt from HR AI Studio."),
            captionsHi = listOf("खुशियों और रोशनी का पावन त्यौहार! ✨ HR AI Studio प्रॉम्प्ट।", "रोशनी का पर्व, दिल से दिल तक। 🪔"),
            primaryColorHex = 0xFFF59E0B,
            secondaryColorHex = 0xFFD97706,
            iconName = "flare"
        ),

        PromptTutorial(
            id = "trend_product_luxury_perfume",
            titleEn = "Luxury Perfume Commercial Studio Photography",
            titleHi = "लग्जरी परफ्यूम कमर्शियल स्टूडियो फोटोग्राफी",
            category = "Product Photography",
            categoryHi = "प्रोडक्ट फोटोग्राफी",
            contentType = ContentType.PHOTO,
            difficulty = Difficulty.INTERMEDIATE,
            requiredInput = RequiredInput.TEXT_ONLY,
            trendStatus = TrendStatus.CLASSIC_STYLE,
            lastVerifiedDate = "Verified October 2026",
            suggestedAspectRatio = "1:1 (Post / E-commerce)",
            promptText = "A high-end commercial product photograph of a luxury crystal perfume bottle with a heavy gold cap, resting on wet black marble slab. Crystal clear water splashes frozen in mid-air around the bottle. Slices of fresh bergamot and dry cedarwood arranged symmetrically in background. Dramatic low-key rim lighting, pristine glass refraction, macro lens sharpness, 8k resolution advertising quality.",
            promptExplanationEn = "Professional studio aesthetic for lifestyle creators, affiliate marketers, and small business brands looking for high-end catalog visuals.",
            promptExplanationHi = "क्रिस्टल परफ्यूम बॉटल, काले संगमरमर पर पानी के छींटे और फ्रेश लेमन-वुडन बैकग्राउंड के साथ प्रोफेशनल विज्ञापन स्टाइल फोटो।",
            visualEffectEn = "Water droplets frozen at 1/8000s shutter speed with razor-sharp glass reflections.",
            visualEffectHi = "पानी की बूंदों का स्टॉप-मोशन इफ़ेक्ट और क्रिस्टल कांच की शानदार रिफ्लेक्शन।",
            supportedTools = listOf(
                ToolReference("Leonardo.Ai", "https://leonardo.ai", "Recommended for product models"),
                ToolReference("Microsoft Copilot", "https://copilot.microsoft.com/images/create", "Free"),
                ToolReference("Canva", "https://www.canva.com", "Template overlays")
            ),
            steps = listOf(
                TutorialStep(1, "Insert Your Product Name", "अपने प्रोडक्ट का नाम डालें", "Replace 'crystal perfume bottle' with your specific product (e.g. watch, sneaker, skincare tube).", "'Perfume bottle' की जगह अपनी घड़ी, क्रीम या स्नीकर का नाम लिखें।"),
                TutorialStep(2, "Select Studio Lighting", "स्टूडियो लाइटिंग चुनें", "Keep 'low-key rim lighting' and 'macro lens sharpness' in the prompt.", "प्रॉम्प्ट में 'rim lighting' बनाए रखें जिससे प्रोडक्ट के किनारे चमकें।"),
                TutorialStep(3, "Add Brand Logo in Canva", "कैनवा में लोगो लगाएं", "Download the render and add your official brand typography in Canva.", "इमेज डाउनलोड करके Canva में अपना ब्रांड लोगो और प्राइस टैग लगाएं।")
            ),
            proTipsEn = listOf("Add 'commercial advertising photography, Hasselblad H6D-100c' for ultra-fine product textures."),
            proTipsHi = listOf("अल्ट्रा-फाइन टेक्सचर के लिए 'commercial advertising photography' शब्द जोड़े रखें।"),
            commonMistakesEn = listOf("Specifying too many background props that steal attention from the primary product."),
            commonMistakesHi = listOf("बैकग्राउंड में बहुत सारी चीजें रख देना जिससे मुख्य प्रोडक्ट छिप जाए।"),
            instagramExportGuideEn = "1:1 Square (1080x1080) or 4:5 portrait for sponsored carousel ads on Instagram.",
            instagramExportGuideHi = "1:1 चौकोर या 4:5 पोर्ट्रेट अनुपात में इंस्टाग्राम स्पॉन्सर्ड पोस्ट्स के लिए एक्सपोर्ट करें।",
            hashtags = listOf("#ProductPhotography", "#CommercialDesign", "#AdvertisingVisuals", "#HRAIStudio", "#LuxuryAesthetic"),
            captionsEn = listOf("Elegance in every drop. 💎 Luxury product visual crafted with HR AI Studio.", "Elevate your brand presence with commercial-grade aesthetics. ✨"),
            captionsHi = listOf("हर बूंद में लग्जरी और स्टाइल। 💎 HR AI Studio कमर्शियल गाइड।", "अपने ब्रांड को दें नया प्रोफेशनल लुक! ✨"),
            primaryColorHex = 0xFF6366F1,
            secondaryColorHex = 0xFF14B8A6,
            iconName = "shopping_bag"
        ),

        PromptTutorial(
            id = "trend_cinematic_drone_flyover",
            titleEn = "Golden Hour Mountain Valley Drone Orbit",
            titleHi = "गोल्डन ऑवर माउंटेन वैली ड्रोन ऑर्बिट",
            category = "Cinematic Camera Movement",
            categoryHi = "सिनेमैटिक कैमरा मूवमेंट",
            contentType = ContentType.VIDEO,
            difficulty = Difficulty.INTERMEDIATE,
            requiredInput = RequiredInput.TEXT_ONLY,
            trendStatus = TrendStatus.REEL_VIRAL,
            lastVerifiedDate = "Verified October 2026",
            suggestedAspectRatio = "9:16 (Vertical Travel Reel)",
            promptText = "Cinematic FPV drone shot flying smoothly over misty Himalayan mountain ridge during sunset golden hour. Deep orange and pink sky, sunlight flaring through pine trees, low hanging valley clouds, ultra-smooth gimbal motion, hyperlapse speed ramping, 4k 60fps nature documentary style.",
            promptExplanationEn = "Creates breathtaking travel reels simulating million-dollar helicopter and FPV drone footage without expensive camera gear.",
            promptExplanationHi = "सूर्यास्त के समय पहाड़ों और देवदार के जंगलों के ऊपर उड़ते हुए FPV ड्रोन का मनमोहक सिनेमैटिक वीडियो।",
            visualEffectEn = "Wide sweeping drone orbit with warm sunburst lens flares and rolling valley fog.",
            visualEffectHi = "पहाड़ों के ऊपर उड़ता कैमरा, बादलों की धुंध और डूबते सूरज की सुनहरी किरणें।",
            supportedTools = listOf(
                ToolReference("RunwayML Gen-3", "https://runwayml.com", "Video Generation"),
                ToolReference("CapCut", "https://www.capcut.com", "Speed ramping"),
                ToolReference("Instagram Reels", "https://www.instagram.com", "Travel audio library")
            ),
            steps = listOf(
                TutorialStep(1, "Copy Movement Instructions", "मूवमेंट निर्देश कॉपी करें", "Copy the camera movement prompt describing orbit speed and height.", "ड्रोन ऑर्बिट और गति का प्रॉम्प्ट कॉपी करें।"),
                TutorialStep(2, "Generate Short Clip", "4-सेकंड क्लिप रेंडर करें", "Use RunwayML or video generation tools to create a 4-second motion clip.", "टूल में 4 सेकंड का मोशन क्लिप तैयार करें।"),
                TutorialStep(3, "Apply Speed Ramp in CapCut", "CapCut में स्पीड रैंप लगाएं", "Slow down the middle from 1.0x to 0.4x as the sun flares, then accelerate out.", "सूरज की किरणें दिखते ही गति 0.4x स्लो करें और फिर सामान्य करें।")
            ),
            proTipsEn = listOf("Combine with atmospheric acoustic guitar or indie travel audio on Instagram for high viral shareability."),
            proTipsHi = listOf("इंस्टाग्राम पर इसे शांत गिटार या सूफी ट्रैवल म्यूजिक के साथ पोस्ट करें।"),
            commonMistakesEn = listOf("Letting the drone camera tilt too violently, breaking the sense of cinematic weight."),
            commonMistakesHi = listOf("कैमरे को बहुत तेजी से घुमाना जिससे देखने वाले को चक्कर आने लगे।"),
            instagramExportGuideEn = "Export 9:16 vertical at 1080x1920, 60fps. Perfect for travel quotes and motivational reels.",
            instagramExportGuideHi = "9:16 वर्टिकल, 60fps। मोटिवेशनल कोट्स और ट्रैवल रील्स के लिए सबसे बेस्ट।",
            hashtags = listOf("#TravelReels", "#DroneShots", "#Wanderlust", "#CinematicNature", "#HRAIStudio"),
            captionsEn = listOf("Chasing horizons that words cannot describe. 🏔️ Golden hour aerial magic.", "Peace found where mountains meet the sky. 🌅"),
            captionsHi = listOf("जहां पर्वत बादलों से मिलते हैं, वहीं सुकून है। 🏔️ HR AI Studio गाइड।", "गोल्डन ऑवर की जादुई शाम। 🌅"),
            primaryColorHex = 0xFF10B981,
            secondaryColorHex = 0xFF3B82F6,
            iconName = "flight"
        ),

        PromptTutorial(
            id = "trend_captions_hashtag_secrets",
            titleEn = "Viral Instagram Reels Hooks & Hashtag Blueprint",
            titleHi = "वायरल इंस्टाग्राम रील्स हुक्स एवं हैशटैग ब्लूप्रिंट",
            category = "Captions and Hashtags Guidance",
            categoryHi = "कैप्शन और हैशटैग गाइडेंस",
            contentType = ContentType.EDITING,
            difficulty = Difficulty.BEGINNER,
            requiredInput = RequiredInput.TEXT_ONLY,
            trendStatus = TrendStatus.NEW_TUTORIAL,
            lastVerifiedDate = "Verified October 2026",
            suggestedAspectRatio = "Universal (Reels & Feed)",
            promptText = "Formula for 3-Second Hook: [Unbelievable Result] + [Time/Effort Constraint] + [Call to Action]. Example: 'Stop scrolling if you want your photos to look like Hollywood movies in 30 seconds. Save this before Instagram hides it.'",
            promptExplanationEn = "A proven framework for writing captions, first-3-second on-screen hooks, and categorized hashtag clusters.",
            promptExplanationHi = "पहले 3 सेकंड में व्यूअर का ध्यान खींचने वाले ऑन-स्क्रीन हुक्स और सही हैशटैग चुनने का फॉर्मूला।",
            visualEffectEn = "Bold 2-line high-contrast text placed in the upper-third of the screen for immediate thumb-stopping impact.",
            visualEffectHi = "स्क्रीन के ऊपरी हिस्से में बोल्ड और साफ 2-लाइन हुक टेक्स्ट जो तुरंत ध्यान आकर्षित करता है।",
            supportedTools = listOf(
                ToolReference("Instagram In-App Text", "https://www.instagram.com", "Free native fonts"),
                ToolReference("CapCut Auto Captions", "https://www.capcut.com", "Free auto subtitle sync"),
                ToolReference("Canva", "https://www.canva.com", "Hook graphic design")
            ),
            steps = listOf(
                TutorialStep(1, "Pick a Strong Hook", "मजबूत हुक चुनें", "Place a curious question or bold statement in the first 1.5 seconds.", "वीडियो के पहले 1.5 सेकंड में एक दिलचस्प सवाल या चौंकाने वाली बात लिखें।"),
                TutorialStep(2, "Use 3x3 Hashtag Method", "3x3 हैशटैग फॉर्मूला लागू करें", "Use 3 broad (#Photography), 3 niche (#AIPrompts), and 3 community (#HRAIStudio) tags.", "3 बड़े हैशटैग, 3 विषय-संबंधित और 3 कम्युनिटी हैशटैग का संयोजन बनाएं।"),
                TutorialStep(3, "Add Strong CTA", "कॉल टू एक्शन (CTA) जोड़ें", "End caption with 'Comment PROMPT and I will share the exact preset!'", "कैप्शन के अंत में 'Save this post' या 'Comment for preset' अवश्य लिखें।")
            ),
            proTipsEn = listOf(
                "Keep captions between 120-250 characters for better algorithm reading.",
                "Avoid using 30 generic tags like #love, #instagood as they trigger spam filters."
            ),
            proTipsHi = listOf(
                "कैप्शन 120 से 250 अक्षरों के बीच रखें ताकि लोग पूरा पढ़ सकें।",
                "30 बेमतलब हैशटैग न लगाएं; 7-10 सटीक हैशटैग सबसे अच्छे होते हैं।"
            ),
            commonMistakesEn = listOf("Writing the entire story in the video without any text hook in the first 2 seconds."),
            commonMistakesHi = listOf("शुरुआत में कोई टेक्स्ट न डालना जिससे दर्शक आगे स्क्रॉल कर जाते हैं।"),
            instagramExportGuideEn = "Place on-screen hook within safe margins (avoid bottom 20% where caption and audio title overlap).",
            instagramExportGuideHi = "टेक्स्ट को सेफ मार्जिन में रखें ताकि वह नीचे कैप्शन या लाइक बटन के पीछे न छिपे।",
            hashtags = listOf("#ReelsGrowth", "#ContentCreatorTips", "#ViralHooks", "#InstagramStrategy", "#HRAIStudio", "#CreatorHacks"),
            captionsEn = listOf(
                "3 mistakes killing your reel reach in 2026. 📉 Read caption to fix them now!",
                "The exact prompt formula top creators won't tell you. 🤫 Save for your next post."
            ),
            captionsHi = listOf(
                "2026 में रील्स रीच बढ़ाने के 3 अचूक नियम! 📉 पूरा कैप्शन पढ़ें।",
                "यह प्रॉम्प्ट सीक्रेट आपके काम आएगा। 🤫 सेव करके रख लें!"
            ),
            primaryColorHex = 0xFFA855F7,
            secondaryColorHex = 0xFF6366F1,
            iconName = "tag"
        )
    )

    override fun getAllPrompts(): Flow<List<PromptTutorial>> = flow {
        emit(samplePrompts)
    }

    override fun getPromptById(id: String): Flow<PromptTutorial?> = flow {
        val item = samplePrompts.find { it.id == id }
        emit(item)
    }

    override fun getFeaturedPrompts(): Flow<List<PromptTutorial>> = flow {
        emit(samplePrompts.filter { it.trendStatus == TrendStatus.HOT_TREND || it.trendStatus == TrendStatus.REEL_VIRAL })
    }

    override fun getPromptsByCategory(category: String): Flow<List<PromptTutorial>> = flow {
        if (category.equals("All", ignoreCase = true) || category.equals("सभी", ignoreCase = true)) {
            emit(samplePrompts)
        } else {
            emit(samplePrompts.filter {
                it.category.contains(category, ignoreCase = true) || it.categoryHi.contains(category, ignoreCase = true)
            })
        }
    }

    override fun getPromptsByContentType(contentType: ContentType): Flow<List<PromptTutorial>> = flow {
        emit(samplePrompts.filter { it.contentType == contentType })
    }

    override fun searchPrompts(query: String): Flow<List<PromptTutorial>> = flow {
        if (query.isBlank()) {
            emit(samplePrompts)
        } else {
            val q = query.trim().lowercase()
            emit(samplePrompts.filter {
                it.titleEn.lowercase().contains(q) ||
                it.titleHi.lowercase().contains(q) ||
                it.promptText.lowercase().contains(q) ||
                it.category.lowercase().contains(q) ||
                it.categoryHi.lowercase().contains(q) ||
                it.hashtags.any { tag -> tag.lowercase().contains(q) } ||
                it.supportedTools.any { tool -> tool.name.lowercase().contains(q) }
            })
        }
    }

    override fun getAllExternalTools(): Flow<List<ExternalTool>> = flow {
        emit(toolsList)
    }
}
