# FPS SDK for Mobile App

> [Google Sheet](https://docs.google.com/spreadsheets/d/1Of9875y_-_dn2J2-_x0p20uUHTSv4bABMf2wtMrqvdA/edit?usp=sharing)
> | [Integration Docs](https://docs.google.com/document/d/1UfxZUblC49d2t8PEpzBo4OvW6GXcx7ubat_Dd8_UCY4/edit?usp=sharing)
> | [Gitlab](https://gitlab.nexdev.net/spu/fps.mobileapp.service.git)

FPS (Fraud Protection System) SDK simplifies the integration of Fraud Protection System capabilities into your mobile applications. It enables seamless device identification and provides enhanced functionality for secure and reliable operations.

## Table of Contents
- [FPS SDK for Mobile App](#fps-sdk-for-mobile-app)
  - [Table of Contents](#table-of-contents)
  - [Platform](#platform)
  - [How it works](#how-it-works)
  - [Stable Table](#stable-table)
    - [Android](#android)
    - [iOS](#ios)
  - [Project Structure](#project-structure)
  - [Getting Started](#getting-started)
    - [Native Android (Kotlin + Java)](#native-android-kotlin--java)
      - [Build \& Run Demo App](#build--run-demo-app)
      - [Package FPS SDK](#package-fps-sdk)
    - [Flutter](#flutter)
      - [Requirements](#requirements)
      - [Build \& Run Demo App](#build--run-demo-app-1)
      - [Package FPS SDK](#package-fps-sdk-1)
    - [React Native](#react-native)
      - [Requirements](#requirements-1)
      - [Build \& Run Demo App](#build--run-demo-app-2)
      - [Package FPS SDK](#package-fps-sdk-2)
  - [Limitation \& Enhancement](#limitation--enhancement)
  - [Changelog](#changelog)

## Platform

| Platform       | Language                        | Status      | Note                |
| -------------- | ------------------------------- | ----------- | ------------------- |
| Native Android | Kotlin                          | Done        |                     |
| Native Android | Java                            | Done        | Share with Kotlin   |
| Native iOS     | Swift                           | Not in plan |                     |
| Native iOS     | Object-C                        | Not in plan |                     |
| Flutter        | Dart (Kotlin + Swift)*          | Done        |                     |
| React Native   | TypeScript (Kotlin + Object-C)* | Done        | using Legacy Module |

> *: With cross-platform (Flutter, RN, ...), the SDK is written in specific language to call native libraries (Android and iOS). It uses platform channels to communicate with the native code.

## How it works
FPS SDK collects various device identifiers and attributes to create a unique fingerprint for each device. Here's how it works:

- **Device Identifiers**: Depending on availability, the SDK collects identifiers such as:
  - **Android Devices**:  `Media DRM ID`, `Android ID`, or `Tagging`. These identifiers are stable across app reinstalls but may change after a factory reset.
  - **iOS Devices**: `Identifier For Vendor`, or `Tagging`. Additionally, the SDK stores these identifiers in the keychain, ensuring stability even after app reinstalls.

> `Tagging` is a random and unique device identifier, which could be stored in internal/~~external~~ device storage.

> See the [table](#stable-table) about stability of each.

- **Additional Data**: The SDK also gathers device settings, signals, and attributes to enhance fingerprinting accuracy. These include:
  - **Is Emulator**: Indicates whether the device is running on an emulator.
  - **Model Name**: The model name of the device.
  - **Manufacturer**: The manufacturer of the device.
  - **Brand**: The brand of the device.
  - **SDK Version**: The Android/iOS SDK version of the device.
  - **Operating System**: The operating system of the device.
  - **Timezone**: The timezone configured on the device.
  - **System Language**: The primary language set on the device ([ISO 639-3 codes](https://en.wikipedia.org/wiki/List_of_ISO_639-2_codes)).
  - **Country**: The country associated with the device's configuration ([ISO 3166-1 alpha-2](https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2)).

- **Data Processing**: All collected information is combined into a single base64-encoded message. This message is then forwarded through the following flow:
  ```
  SDK -> App -> App Backend Service -> FPS Service
  ```

This process ensures reliable device identification while maintaining a seamless integration experience.

## Stable Table

### Android
| Identifier Type | Stability after App Reinstall | Stability after Factory Reset |
| --------------- | ----------------------------- | ----------------------------- |
| Media DRM ID    | Stable                        | May Change                    |
| Android ID      | Stable                        | May Change                    |
| Tagging         | May Change                    | May Change                    |

### iOS
| Identifier Type       | Stability after App Reinstall | Stability after Factory Reset |
| --------------------- | ----------------------------- | ----------------------------- |
| Identifier For Vendor | Stable                        | May Change                    |
| Tagging (in Keychain) | Stable                        | May Change                    |

## Project Structure
```
├── docs/                     # Platform-specific Documentation for SDKs
│   ├── Flutter SDK/          
│   ├── Kotlin_Java SDK/      
│   ├── React Native SDK/     
├── src/                      # Platform-specific Source code for SDKs
│   ├── Flutter/              
│   ├── Java_Kotlin/          
│   ├── ReactNative/          
├── .gitignore                # Git ignore file
├── README.md                 # Main project README
```

## Getting Started
This section would guide developers how to start projects and contribute to FPS SDK.
>**Note**: Any changes to the SDK should be reflected in the documentation (Shared `README.md` & Specific-platform `README.md`). Please update the documentation accordingly.

### Native Android (Kotlin + Java)
In `Java_Kotlin` folder, you will find the following project structure:
```
├── fpsCore/               # FPS SDK core implementation
├── sdk/                   # FPS SDK interface
├── demoapp/               # Demo app for FPS SDK
```

#### Build & Run Demo App
1. Open the `Java_Kotlin` project in Android Studio.
2. Resync Gradle project.
3. Run the demo app.
  
#### Package FPS SDK
1. Open the `Java_Kotlin` project in Android Studio.
2. Resync Gradle project.
3. Build the project.
4. The generated SDK will be located in
   1.  `fpsCore/build/outputs/aar/` directory.
   2.  `sdk/build/outputs/aar/` directory.

### Flutter
Project structure for Flutter SDK is as follows:
> **Note**: Please keep in mind the structure of the Flutter SDK. It is important to package and deliver the SDK correctly to ensure proper functionality.
```
├── android/               # Android platform-specific code
├── ios/                   # iOS platform-specific code
├── lib/                   # Dart code for Flutter SDK
├── pubspec.yaml           # Flutter package configuration
├── README.md              # Tutorial for integration
```
#### Requirements
Specified in `Readme.md` in `Flutter SDK`.

#### Build & Run Demo App
1. Open the `Flutter` project in Visual Studio Code or Android Studio.
2. Run command `flutter pub get` to install dependencies.
3. Run the demo app using `flutter run` command.
4. The demo app will be launched on the connected device or emulator.

#### Package FPS SDK
1. Remove the redundant files and folders from the project.
2. Archive the project as project structure above.

### React Native
Project structure for React Native SDK is as follows:
> **Note**: Please keep in mind the structure of the React Native SDK. It is important to package and deliver the SDK correctly to ensure proper functionality.
```
├── android/               # Android platform-specific code
├── ios/                   # iOS platform-specific code
├── lib/                   # JavaScript code for React Native SDK
├── package.json           # React Native package configuration
├── README.md              # Tutorial for integration
```
#### Requirements
Specified in `Readme.md` in `ReactNative`.

#### Build & Run Demo App
1. Open the `ReactNative` project in Visual Studio Code or Android Studio.
2. Run command `npm install` to install dependencies.
3. For iOS, please run `cd ios && pod install` to install CocoaPods dependencies.
4. Run the demo app using `npm start` command.
5. The demo app will be launched on the connected device or emulator.

#### Package FPS SDK
1. Remove the redundant files and folders from the project.
2. Archive the project as project structure above.

## Limitation & Enhancement
  * There is a **duplicated codes** between platform SDK. This issue could be addressed by using private package registry.
  * **Enhance device fingerprint** by combining all available device signals and attributes into a single identifier
  
## Changelog
* 1.0.6
    * Initial Release for **React Native** library
    * Fix bug on handling Burmese numerals (and similar non-Arabic numerals)
* 1.0.5
    * **Flutter Plugin**:
      * Support iOS platform
      * Enhance Session Management
* 1.0.4
    * Initial Release for **Flutter Plugin**
* 1.0.3
  * **Native Android**
    * Various bug fixes and optimizations
* 1.0.2
  * **Native Android**
    * Various bug fixes and optimizations
* 1.0.1
  * **Native Android**
    * Various bug fixes and optimizations
* 1.0.0
    * Initial Release for **Native Android** SDK