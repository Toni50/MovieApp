plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.moviesapp"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.example.moviesapp"
        minSdk = 26
        targetSdk = 36
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
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Retrofit core
    implementation(libs.retrofit)

    // Retrofit Gson converter (to parse JSON into Kotlin objects)
    implementation(libs.converter.gson)

    // Room runtime
    implementation(libs.androidx.room.runtime)

    // Room Kotlin Extensions (optional but recommended for suspend functions & coroutines)
    implementation(libs.androidx.room.ktx)

    // Room compiler for annotation processing
    ksp(libs.androidx.room.compiler)

    // Hilt Android library
    implementation(libs.hilt.android)

    //import androidx.hilt.navigation.compose.hiltViewModel
    implementation(libs.androidx.hilt.navigation.compose)

    // Hilt compiler (annotation processor)
    ksp(libs.hilt.android.compiler)

    //Image Loader
    implementation(libs.coil.compose)

    //navigation
    implementation(libs.androidx.navigation.compose)

    //material icons
    implementation (libs.androidx.compose.material.icons.extended)
}