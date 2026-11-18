# 🍋 Little Lemon - Android Application

[![Coursera](https://img.shields.io/badge/Coursera-Meta%20Android%20Developer%20Capstone-blue.svg)](https://www.coursera.org/professional-certificates/meta-android-developer)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.2.10-purple.svg)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Material%203-green.svg)](https://developer.android.com/jetpack/compose)

Little Lemon is a modern Android mobile application for a family-owned Mediterranean restaurant based in Chicago. Developed as the final **Capstone Project** for the **Meta Android Developer Professional Certificate** on Coursera, this application demonstrates modern Android app development practices using declarative UI with Jetpack Compose, asynchronous networking with Ktor, offline caching with Room Database, and local state persistence.

---

## 📱 Features

- **User Onboarding & Session Management**:
  - Interactive onboarding screen to collect user details (First Name, Last Name, Email) with input validation.
  - Session persistence using `SharedPreferences` to maintain logged-in state across app launches.
  - Profile screen displaying registered user details with a complete Log Out capability that clears user session data and resets the backstack.

- **Dynamic Navigation Flow**:
  - Built using Jetpack **Navigation Compose** (`NavHost` and `NavController`).
  - Automatic initial destination routing: opens `Home` if the user is logged in, or `Onboarding` if logged out.
  - Backstack management ensuring proper back button behavior between `Profile`, `Home`, and `Onboarding`.

- **Hero Banner & Brand Styling**:
  - Distinctive Little Lemon brand visual language (`#495E57` primary green and `#F4CE14` primary yellow).
  - Restaurant metadata section featuring location info ("Chicago") and restaurant description.
  - System bar edge-to-edge support with `safeDrawingPadding` for seamless dark and light theme adaptability.

- **Remote API Fetching & Offline Caching**:
  - Asynchronous network calls via **Ktor Client** and **Kotlinx Serialization** to fetch menu items from a remote JSON endpoint.
  - Local database caching using **Room Database** (`MenuItemRoom` entity & `MenuDao`) to store menu items for offline accessibility.

- **Search & Category Breakdown Filtering**:
  - Real-time search bar with instant filtering by menu item titles.
  - Category breakdown filter pills (`Starters`, `Mains`, `Desserts`, `Drinks`) dynamically generated from menu data.
  - Combined filtering using Kotlin collection processing memoized with Compose `remember`.

- **UI Performance Optimization**:
  - High-performance `LazyColumn` list rendering with keyed item diffing (`items(filteredItems, key = { it.id })`).
  - Integrated **Glide Compose** image loading combined with zero-latency local fallback drawables.

---

## 🛠️ Tech Stack & Architecture

- **Language**: Kotlin 2.2.10
- **UI Framework**: Jetpack Compose with Material 3
- **Navigation**: Jetpack Navigation Compose
- **Networking**: Ktor Client (`ktor-client-android`, `ktor-client-content-negotiation`, `ktor-serialization-kotlinx-json`)
- **JSON Serialization**: Kotlinx Serialization (`kotlinx-serialization-json`)
- **Database & Local Storage**: Room Database (`androidx.room`), KSP (`com.google.devtools.ksp`), SharedPreferences
- **Image Loading**: Glide Compose (`com.github.bumptech.glide:compose`)
- **Build System**: Gradle Version Catalog (`libs.versions.toml`) with Android Gradle Plugin (AGP) 9.5

---

## 📂 Project Structure

```
com.example.littlelemon/
├── MainActivity.kt           # Activity entry point, Edge-to-Edge setup & Ktor API fetch trigger
├── NavigationComposable.kt   # NavHost setup and start destination resolution
├── Destinations.kt           # Navigation route destinations (Onboarding, Home, Profile)
├── Onboarding.kt             # User registration screen & input validation
├── Home.kt                   # Home screen, Hero banner, Search, Category filter & Menu list
├── Profile.kt                # Profile screen displaying user details & Logout button
├── Network.kt                # Ktor DTOs (MenuNetwork & MenuItemNetwork)
├── Database.kt               # Room Entity (MenuItemRoom), DAO (MenuDao) & AppDatabase singleton
└── ui/theme/                 # Material 3 Color, Theme & Typography definitions
```

---

## 🚀 Getting Started

### Prerequisites
- **Android Studio**: Ladybug / 2024.2+ or latest release
- **JDK**: Version 11 or higher
- **Android SDK**: Min SDK 33 / Target SDK 37

### Installation & Run

1. **Clone the repository**:
   ```bash
   git clone https://github.com/Soumasish2005/little-lemon.git
   cd little-lemon
   ```

2. **Open in Android Studio**:
   - Open Android Studio and choose **Open an Existing Project**.
   - Select the `little-lemon` root folder and allow Gradle sync to complete.

3. **Build and Run**:
   - Select your target Android device or emulator.
   - Click **Run 'app'** (`Shift + F10`).

---

## 🎓 Certificate & Acknowledgments

This project was built as the Capstone Project for the **[Meta Android Developer Professional Certificate](https://www.coursera.org/professional-certificates/meta-android-developer)** on Coursera. Special thanks to Meta and Coursera for providing the project specification, assets, and design guidelines.
