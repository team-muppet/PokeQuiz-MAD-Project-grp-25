# PokéQuiz

PokéQuiz is a mobile app designed for Pokémon enthusiasts to explore Pokémon data and test their knowledge through interactive quizzes. The app integrates data from APIs like **pokeapi.co** and **pokemontcg.co** to provide detailed Pokémon information and an engaging quiz experience.

![Collage](https://github.com/user-attachments/assets/b2bbf5f1-5fc5-4fe0-a088-c57574b929d3)

## Features

- **Home Screen**: Quick access to all quiz game modes, Pokédex, scoreboard, and your profile.
- **Pokémon List**: Browse Pokémon species with images, names, and numbers.
- **Pokémon Details**: View detailed information about individual Pokémon, including evolution chains, stats, cries, and sprites.
- **Interactive Quizzes**:
  - **Silhouette Quiz**: Identify Pokémon based on their silhouettes (100% offline using bundled assets).
  - **Card Quiz**: Identify Pokémon from progressively less blurred trading card images.
  - **Details Quiz**: Guess the correct Pokémon by matching stats like types, weight, and habitat.
- **Scoreboard**: See a ranked list of Pokémon trainer scores and track how your quiz accuracy compares against classic champions.
- **User Profile**: Personalize your trainer profile with custom photos from your gallery, edit your trainer name, and track quiz statistics. All user data is stored self-contained and offline on your device (no sign-in or cloud services required).

## Download & Installation

Whenever changes are pushed to `main`, GitHub Actions automatically compiles and signs a release APK.

To install PokéQuiz on your Android phone:
1. Go to the [Releases](https://github.com/user-attachments) page of this repository.
2. Download the latest `PokeQuiz-v1.0.X.apk` file directly to your phone.
3. Tap the file to install (if prompted, allow "Install unknown apps" in Android settings for your browser or file manager).

## Tech Stack & Architecture

- **Language**: Kotlin
- **UI Toolkit**: Jetpack Compose & Material 3
- **Dependency Injection**: Dagger Hilt
- **Networking**: Retrofit, Apollo GraphQL (Beta PokéAPI GraphQL & Pokémon TCG API)
- **Image Loading**: Coil (supports SVG, network, and local device assets)
- **Local Persistence**: Android SharedPreferences & internal file storage (zero cloud/Firebase lock-in)
- **CI/CD**: GitHub Actions workflow for automated release APK builds and release tagging
