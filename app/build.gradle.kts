import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-parcelize")
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
}

android {
    namespace = "com.genzen.zenspire"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.genzen.zenspire"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())
        buildConfigField("String", "GEMINI_API_KEY", "\"${properties.getProperty("GEMINI_API_KEY")}\"")
        buildConfigField("String", "MAPS_API_KEY", "\"${properties.getProperty("MAPS_API_KEY")}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        android.buildFeatures.buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.firestore)
    implementation(libs.play.services.maps)
    implementation("com.google.android.gms:play-services-location:21.3.0")
    implementation("com.google.android.libraries.places:places:3.3.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.6.2")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.4")
    implementation("com.google.ai.client.generativeai:generativeai:0.3.0")
    implementation("io.noties.markwon:core:4.6.2")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}