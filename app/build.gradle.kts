plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.it_capstone_archive"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.it_capstone_archive"
        minSdk = 26
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
}

dependencies {
    // Core Android libraries
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.13.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-storage")

    // Google Play Services
    implementation("com.google.android.gms:play-services-auth:21.3.0")

    // MongoDB (ensure versions are compatible)
    implementation("org.mongodb:mongodb-driver-sync:4.9.1") {
        exclude(group = "org.mongodb", module = "bson")
    }
    implementation("org.mongodb:bson:4.9.1")

    // DrawerLayout is already included in 'material'
    // implementation("androidx.drawerlayout:drawerlayout:1.2.0")
}