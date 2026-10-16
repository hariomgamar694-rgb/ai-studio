# HR AI Studio — Trending Prompts & Tutorials

<p align="center">
  <strong>Instagram Trending Prompts & Creative Workflows</strong><br>
  <em>Discover. Copy. Create. (खोजें. कॉपी करें. बनाएं.)</em>
</p>

---

## 📱 About the App

**HR AI Studio** is a native Android application built with Kotlin and Jetpack Compose. It serves as a curated educational content library and tutorial guide for content creators, Instagram reel makers, and digital artists.

> ⚠️ **Educational Content Notice:**  
> HR AI Studio is an educational resource library. It is **NOT** an AI image/video generator or editor. It provides curated, high-impact creative prompts, technical parameters, and step-by-step guides so creators can safely execute them using official, verified external tools (such as Meta AI, Microsoft Copilot, CapCut, and Leonardo.Ai).

---

## ✨ Key Features

1. **Brand Identity & Dark Navy Aesthetics:**
   - Premium dark navy backdrop (`#070B14`, `#0B1120`) with electric blue and neon violet glowing accents.
   - Custom HR monogram launcher icon and brand header with creative sparkle.

2. **Dual Language Support (Hindi & English):**
   - **Hindi default:** Complete titles, descriptions, step-by-step instructions, and tips localized in clear Hindi.
   - Seamless one-tap language switch button in the header and Settings.

3. **Curated Content Categories:**
   - **Royal and 3D Photo Styles:** Glowing neon wings, obsidian thrones, and luxury ethnic sherwani/lehenga portraits.
   - **Photo to Video Tutorials:** Camera dolly push-ins, dynamic smoke mist, and beat-synced motion prompts.
   - **Cinematic Reels:** Cyberpunk rain streets, neon reflections, and 85mm bokeh portraits.
   - **3D Character & Cartoon Styles:** Pixar-Disney cute avatars and caricatures.
   - **AI Photo Editing:** Moody dark teal & orange color grading workflows and slider recipes.
   - **Festival Photo Styles:** Golden diya candlelight, floating sparks, and traditional celebrations.
   - **Product Photography:** Studio low-key rim lighting, water splashes, and commercial packaging.
   - **Reels Hooks & Hashtags Guidance:** 3-second viral hook formulas and hashtag cluster blueprints.

4. **10-Point Trend Detail Page:**
   - Visual aesthetic gradient header with aspect ratio and input requirement chips.
   - Visual effect breakdown in Hindi and English.
   - Verified external tools needed with direct website launchers.
   - Ready-to-use high-contrast **Copyable Prompt Card** (with 1-tap "Copy Prompt" and "+ Hashtags" buttons).
   - Numbered step-by-step instructions.
   - Pro tips for better prompt rendering.
   - Common mistakes and troubleshooting guidelines.
   - How to download and format for Instagram (9:16 vertical resolution, 60fps, audio sync).
   - Related prompt suggestions.

5. **Official External Tools Directory:**
   - Curated directory including Meta AI, Instagram Edits, CapCut, Microsoft Copilot / Designer, Leonardo.Ai, Canva, and RunwayML.
   - Explains supported workflows, verified free tier info, and regional notices.
   - Direct, safe external browser launcher.

6. **Local Persistence (Room Database):**
   - Save/bookmark tutorials and prompts for offline reading.
   - Persistent across app sessions without requiring accounts or cloud servers.

7. **Search, Filters & Sorting:**
   - Instant search across prompt text, titles, tools, and hashtags in Hindi and English.
   - Filter chips by format (Photo, Video, Editing), category, and difficulty.
   - Sort by latest added, alphabetical (A–Z), and difficulty.

---

## 🛠️ Architecture & Tech Stack

- **Platform:** Android (Min SDK 24, Target SDK 36)
- **Language:** Kotlin 2.2.10
- **UI Framework:** Jetpack Compose (Material 3)
- **Architecture:** MVVM (Model-View-ViewModel) with Clean Repository Pattern
- **Persistence:** Android Jetpack Room Database (`SavedPromptDao`, `AppDatabase`)
- **Concurrency:** Kotlin Coroutines & `StateFlow` / `collectAsStateWithLifecycle`
- **Navigation:** Jetpack Compose Navigation & Type-safe state handling with `BackHandler`
- **Testing:** Robolectric JVM tests for Critical User Journeys

---

## 🚀 Installation & Running

### Requirements
- Android Studio Ladybug / Meerkat or later
- JDK 17 or later
- Android device or emulator running Android 7.0+ (API 24+)

### Steps
1. Clone or download the project files.
2. Open the project in Android Studio.
3. Allow Gradle to synchronize dependencies.
4. Run the app on an Android device or emulator:
   ```bash
   gradle assembleDebug
   ```
5. Run tests:
   ```bash
   gradle :app:testDebugUnitTest
   ```

---

## 📄 License & Attribution

- Built as part of **HR AI Studio**.
- All mentioned external tool trademarks (Meta AI, Instagram, CapCut, Microsoft Copilot, Runway) belong to their respective copyright holders.
