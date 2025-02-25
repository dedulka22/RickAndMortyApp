plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.devtools.ksp)
}

android {
    namespace = "com.example.rickyandmortyapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.rickyandmortyapp"
        minSdk = 29
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
    dependencies {
        // Navigation
        implementation(libs.voyager.navigator)
        implementation(libs.voyager.bottom.sheet.navigator)
        implementation(libs.voyager.tab.navigator)

        // Retrofit + OkHttp
        implementation(libs.retrofit)
        implementation(libs.retrofit.gson)
        implementation(libs.okhttp)
        implementation(libs.okhttp.logging)

        // Kotlin Coroutines
        implementation(libs.coroutines.core)
        implementation(libs.coroutines.android)

        // Koin
        implementation(libs.koin.android)
        implementation(libs.koin.compose)

        // Jetpack Compose
        implementation(libs.androidx.ui)
        implementation(libs.androidx.ui.graphics)
        implementation(libs.androidx.ui.tooling.preview)
        implementation(libs.androidx.material3)
        implementation(libs.androidx.activity.compose)

        // Paging 3
        implementation(libs.paging.runtime)
        implementation(libs.paging.compose)

        // Image loading
        implementation(libs.coil.compose)
        implementation(libs.coil.network.okhttp)

        // AndroidX Core KTX
        implementation(libs.androidx.core.ktx)

        // ViewModel a StateFlow
        implementation(libs.androidx.lifecycle.viewmodel.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)

        // Room
        implementation(libs.room.runtime)
        implementation(libs.room.ktx)
        ksp(libs.room.compiler)

        // Testing
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        androidTestImplementation(libs.androidx.ui.test.junit4)
        debugImplementation(libs.androidx.ui.tooling)
        debugImplementation(libs.androidx.ui.test.manifest)
    }

}