# Tourism

A Tinder-like Android app where you swipe through travel destinations instead of people.

Tourism helps users discover destinations through a simple card-based browsing experience. The app is built as a native Android application in Java and uses Firebase services for authentication, cloud data, and image storage.

## Features

- Browse and discover travel destinations
- Swipe-based destination exploration
- User authentication with Firebase Authentication
- Destination data stored with Cloud Firestore
- Destination images loaded from Firebase Storage
- Image loading and caching with Glide and Picasso
- Android Navigation Component for screen navigation
- Material Design components and ConstraintLayout-based UI

## Tech stack

- **Language:** Java
- **Platform:** Android
- **Build system:** Gradle Kotlin DSL
- **Android Gradle Plugin:** 8.1.4
- **Compile SDK:** 34
- **Minimum SDK:** 24 (Android 7.0)
- **Target SDK:** 34
- **Java compatibility:** Java 8
- **Firebase Authentication:** 22.3.0
- **Cloud Firestore:** 24.9.1
- **Firebase Storage:** 20.3.0
- **AndroidX Navigation:** 2.7.5
- **Material Components:** 1.10.0
- **Glide:** 4.12.0
- **Picasso:** 2.71828

## Requirements

- Android Studio with Android SDK support
- JDK 8 or a compatible newer JDK supported by your Android Studio installation
- Android SDK Platform 34
- An Android device or emulator running API 24 or higher
- A Firebase project configured for the application

## Getting started

1. Clone the repository:

   ```bash
   git clone https://github.com/ritzvincentvculanag/tourism.git
   cd tourism
   ```

2. Open the project in Android Studio.
3. Create or select a Firebase project in the Firebase Console.
4. Register the Android application using the application ID:

   ```text
   io.github.rmmc.rmmctourism
   ```

5. Download the Firebase configuration file and place it at:

   ```text
   app/google-services.json
   ```

6. Enable the Firebase services required by the app, including Authentication, Cloud Firestore, and Storage.
7. Allow Gradle to sync, connect an Android device or start an emulator, and run the `app` configuration.

> **Security note:** Do not commit production credentials or sensitive Firebase configuration to a public repository. Use appropriate Firebase Security Rules and review the configuration before publishing a release build.

## Build from the command line

Build a debug APK with the Gradle wrapper:

```bash
./gradlew assembleDebug
```

On Windows:

```powershell
.\gradlew.bat assembleDebug
```

The generated APK is written to:

```text
app/build/outputs/apk/debug/app-debug.apk
```

## Testing

Run local unit tests with:

```bash
./gradlew test
```

Run instrumentation tests on a connected device or emulator with:

```bash
./gradlew connectedAndroidTest
```

## Project structure

```text
.
├── app/                    # Android application module
│   ├── src/main/           # Java source code and Android resources
│   ├── build.gradle.kts    # App module configuration
│   └── google-services.json # Firebase configuration
├── build.gradle.kts        # Root Gradle configuration
├── settings.gradle.kts    # Project and repository settings
├── gradlew                 # Gradle wrapper for macOS/Linux
└── gradlew.bat             # Gradle wrapper for Windows
```

## Application configuration

| Property | Value |
| --- | --- |
| Application ID | `io.github.rmmc.rmmctourism` |
| Compile SDK | 34 |
| Target SDK | 34 |
| Minimum SDK | 24 |
| Version | 1.0 |

## Contributing

Contributions are welcome. To contribute:

1. Fork the repository.
2. Create a feature branch.
3. Make your changes and add tests where appropriate.
4. Verify that the project builds successfully.
5. Open a pull request with a clear description of your changes.

## License

No license has been specified for this repository yet.
