# Medicine Lookup (med)

A modern Android application built with **Kotlin** and **Jetpack Compose (Material 3)** designed to help users search for medications, view their chemical formulations, and discover cost-effective alternatives sharing the exact same active composition.

---

## 📱 Features

- **Medicine Search**: Search for brand and generic medications by name with instant lookup.
- **Formula & Composition Details**: Displays chemical formula (e.g., $C_9H_8O_4$, $C_8H_9NO_2$), generic name, brand name, and pricing.
- **Equivalent Alternatives Finder**: Automatically finds and lists alternative medications that share the exact same active chemical formula.
- **Price-Optimized Recommendations**: Equivalent alternatives are sorted from lowest to highest price, prominently highlighting the most affordable option as **Recommended**.
- **Interactive Exploration**: Tap on any listed equivalent medicine to inspect its details and view further matching alternatives.
- **Theme Support**: Seamless toggle between Light and Dark modes.

---

## 🛠️ Tech Stack & Architecture

- **Language**: [Kotlin](https://kotlinlang.org/)
- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) with Material 3 design system
- **Architecture**: MVVM (Model-View-ViewModel)
- **State Management**: Kotlin Coroutines & `StateFlow`
- **Build System**: Gradle with Kotlin DSL (`build.gradle.kts`)
- **Android Target**:
  - `minSdk`: 24 (Android 7.0 Nougat)
  - `targetSdk` / `compileSdk`: 35 (Android 15)
  - Java compatibility: Java 11

---

## 📂 Project Structure

```text
med/
├── app/
│   ├── build.gradle.kts          # App-level build configurations & dependencies
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml
│           └── java/com/example/med/
│               ├── MainActivity.kt               # Main Compose UI & App entry point
│               ├── models/
│               │   └── Medicine.kt               # Medicine data model
│               ├── ui/theme/                     # Theme, Typography, and Color palettes
│               │   ├── Color.kt
│               │   ├── Theme.kt
│               │   └── Type.kt
│               └── viewmodels/
│                   └── MedicineViewModel.kt      # Business logic, state & medicine database
├── gradle/                                       # Gradle wrapper files
├── build.gradle.kts                              # Root project Gradle configuration
└── settings.gradle.kts                           # Module & repository settings
```

---

## 🚀 Getting Started

### Prerequisites

- **Android Studio** (Ladybug / Iguana or later recommended)
- **JDK 11** or higher
- Android SDK with Platform 35 installed
- Android Emulator or physical device running Android 7.0 (API 24) or above

### Setup & Run

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd med
   ```

2. **Open in Android Studio**:
   - Launch Android Studio.
   - Select **Open** and choose the `med` project directory.
   - Allow Gradle to sync project dependencies.

3. **Build & Run**:
   - Connect an Android device or start an emulator.
   - Press **Run** (`Shift + F10`) or execute via command line:
     ```bash
     ./gradlew installDebug
     ```

---

## 🧪 Testing

Run unit and instrumentation tests:

```bash
# Run unit tests
./gradlew test

# Run Android connected instrumentation tests
./gradlew connectedAndroidTest
```
