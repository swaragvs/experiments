plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.swarag.laplock"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.swarag.laplock"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // OkHttp for network requests
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    // Core dependencies for Android KTX
    implementation(libs.androidx.core.ktx)

    // Lifecycle components for ViewModel, LiveData, etc.
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Activity Compose for Compose-based activities
    implementation(libs.androidx.activity.compose)

    // Jetpack Compose BOM (Bill of Materials) for Compose version management
    implementation(platform(libs.androidx.compose.bom))

    // Compose UI and Graphics libraries (BOM-managed)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)

    // Compose tooling for Preview, etc. (BOM-managed)
    implementation(libs.androidx.ui.tooling.preview)

    // Material3 design components for Compose (BOM-managed)
    implementation(libs.androidx.material3)

    // ✅ Material Icons Extended (BOM-managed, no version needed)
    implementation("androidx.compose.material:material-icons-extended")

    // Unit testing libraries
    testImplementation(libs.junit)

    // Android instrumentation testing libraries
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Compose BOM for Android instrumentation tests
    androidTestImplementation(platform(libs.androidx.compose.bom))

    // UI testing with Compose
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Debugging and tooling for Compose
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
