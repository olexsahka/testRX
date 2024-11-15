import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.testgitapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.testgitapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val keystoreFile = project.rootProject.file("filename.properties")
        val properties = Properties()
        properties.load(keystoreFile.inputStream())

        //return empty key in case something goes wrong
        val apiKey = properties.getProperty("API_KEY") ?: ""

        buildConfigField(
            type = "String",
            name = "API_KEY",
            value = apiKey
        )
    }
    buildFeatures {
        buildConfig = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.rxandroid)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.rxjava3)
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.firebase.crashlytics.buildtools)
    implementation(libs.akarnokd.rxjava3.retrofit.adapter)
    implementation(libs.adapter.rxjava3.v290)


    implementation (libs.adapter.rxjava3)
    implementation (libs.converter.gson)
    implementation (libs.retrofit)

    implementation (libs.logging.interceptor)
    implementation (libs.okhttp)



    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}