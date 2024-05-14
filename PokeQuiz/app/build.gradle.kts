plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.googleGmsServices)
    alias(libs.plugins.org.jetbrains.kotlin.kapt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.serilization)
    alias(libs.plugins.apollo)
}

android {
    namespace = "com.example.pokequiz"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.pokequiz"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.12"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

apollo {
    service("service") {
        packageName.set("com.example.pokequiz")
    }
}

dependencies {
    // Firebase
    implementation (libs.firebase.ui.storage)
    implementation(libs.glide)
    annotationProcessor (libs.glide.compiler)

    //Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    kapt(libs.hilt.compiler)

    //Apollo
    implementation(libs.apollo.runtime)

    //Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)

    //Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    //Json
    implementation(libs.kotlinx.serialization.json)

    //Live data
    implementation(libs.androidx.runtime.livedata)

    //Icons https://github.com/DevSrSouza/compose-icons
    implementation(libs.font.awesome) // https://github.com/DevSrSouza/compose-icons/blob/master/font-awesome/DOCUMENTATION.md
    implementation(libs.icons.tabler.icons) // https://github.com/DevSrSouza/compose-icons/blob/master/tabler-icons/DOCUMENTATION.md

    implementation(libs.androidx.navigation.compose)
    implementation(platform(libs.google.firebase.bom))
    implementation(libs.google.firebase.firestore)
    implementation(libs.google.firebase.auth)
    implementation(libs.google.firebase.storage)
    implementation(libs.google.android.gms.play.services)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}