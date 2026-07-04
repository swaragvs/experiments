# TensorVision

TensorVision is an Android application that leverages TensorFlow Lite and CameraX to perform real-time computer vision tasks on mobile devices. The app demonstrates efficient on-device machine learning capabilities using TensorFlow Lite with GPU acceleration support.

## Features

- Real-time camera preview using CameraX
- TensorFlow Lite integration for on-device inference
- GPU acceleration support for improved performance
- Modern Android development practices using Kotlin
- Material Design components for UI

## Technical Specifications

- **Minimum SDK:** 24 (Android 7.0)
- **Target SDK:** 35
- **Programming Language:** Kotlin
- **Build System:** Gradle (Kotlin DSL)

## Dependencies

### Core Android Libraries
- AndroidX Core KTX
- AppCompat
- Material Design Components
- ConstraintLayout

### Camera
- CameraX Core
- CameraX Camera2
- CameraX Lifecycle
- CameraX View

### Machine Learning
- TensorFlow Lite
- TensorFlow Lite Support
- TensorFlow Lite GPU Delegate
- TensorFlow Lite GPU API

## Building the Project

1. Clone the repository
```bash
git clone https://github.com/swaragvs/TensorVision.git
```

2. Open the project in Android Studio

3. Sync the project with Gradle files

4. Build and run the application on your device or emulator

## Configuration

The application is configured with:
- View Binding enabled for easier view access
- Java 17 compatibility
- ProGuard optimization for release builds
- Resource shrinking enabled for release builds

## Version Information
- Current Version: 1.1 (versionCode: 2)
- Package Name: app.yolo.tflite

## License

This project is licensed under the terms found in the [LICENSE](LICENSE) file.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

---

Developed with ❤️ by [swaragvs](https://github.com/swaragvs)