# What's Cooking 🍳

A modern Kotlin Multiplatform application that showcases recipes and meals from around the world
using TheMealDB API.

## Features

- 📱 **Cross-platform**: Runs on both Android and iOS
- 🎨 **Modern UI**: Built with Jetpack Compose Multiplatform
- 🏗️ **Clean Architecture**: Follows CLEAN principles with clear separation of concerns
- 🔄 **Reactive**: Uses Kotlin Flows for reactive data management
- 💉 **Dependency Injection**: Powered by Koin
- 🌐 **Networking**: Ktor client for API communication
- 🖼️ **Image Loading**: Coil for efficient image loading and caching

## Architecture

The app follows Clean Architecture principles with clear separation between layers:

### Layers

- **Presentation Layer** (`ui/`)
    - ViewModels manage UI state and business logic
    - Composable screens handle UI rendering
    - Each feature has its own ViewModel and UI state

- **Domain Layer** (Repositories)
    - Repository pattern abstracts data sources
    - Provides clean API for ViewModels
    - Handles data transformation

- **Data Layer** (`networking/`, `api/`)
    - API services handle network communication
    - DTOs for API responses
    - Network error handling

### Key Components

- **Dependency Injection**: Koin modules organize dependencies by feature
- **Navigation**: Type-safe navigation with Compose Navigation
- **State Management**: Sealed interfaces for UI states (Loading, Content, Error)
- **Error Handling**: Consistent error handling with proper logging

## Project Structure

```
composeApp/src/
├── commonMain/kotlin/one/vitaliy/whatscooking/
│   ├── categories/          # Categories feature
│   │   ├── api/            # API models
│   │   ├── detail/         # Category detail screen
│   │   └── list/           # Categories list screen
│   ├── di/                 # Dependency injection modules
│   ├── homepage/           # Homepage feature
│   │   └── api/           # Homepage repository
│   ├── mealdetail/         # Meal detail feature
│   ├── networking/         # Network layer
│   ├── randommeal/         # Random meal feature
│   └── ui/                 # Shared UI components and theme
├── androidMain/            # Android-specific code
└── iosMain/                # iOS-specific code
```

## Tech Stack

- **Kotlin Multiplatform**: Share code across platforms
- **Jetpack Compose Multiplatform**: Modern declarative UI
- **Ktor**: HTTP client for API calls
- **Kotlinx Serialization**: JSON parsing
- **Koin**: Dependency injection
- **Coil**: Image loading
- **Navigation Compose**: Type-safe navigation
- **Napier**: Multiplatform logging

## Build and Run

**You will need** to provide an API key to build this application. You can use test key "1" and 
update the version of the API to v1 if you don't have any

MEAL_DB_API_KEY=your_api_key_here
API_NINJAS_API_KEY=your_api_key_here


### Android Application

Build and run the Android app from the terminal:

**macOS/Linux:**

```shell
./gradlew :composeApp:assembleDebug
```

**Windows:**

```shell
.\gradlew.bat :composeApp:assembleDebug
```

Or use the run configuration from your IDE's toolbar.

### iOS Application

Open the `/iosApp` directory in Xcode and run the project, or use the run configuration from your
IDE's toolbar.

## API

This app uses [TheMealDB API](https://www.themealdb.com/api.php) and [API Ninjas Recipe API](https://api-ninjas.com/api/recipe) to fetch recipe data.

## Code Quality

- ✅ CLEAN Architecture principles
- ✅ Comprehensive KDoc documentation
- ✅ Consistent error handling
- ✅ Centralized string resources
- ✅ Type-safe navigation
- ✅ Proper separation of concerns
- ✅ Repository pattern for data access

## Future Improvements

- Add local caching with SQLDelight
- Implement search functionality
- Add favorites feature
- Implement offline support
- Add unit and UI tests
- Localization support

---

Learn more
about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)
