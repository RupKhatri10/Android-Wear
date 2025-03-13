plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.group2_midterm"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.group2_midterm"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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

    implementation(libs.play.services.wearable)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.recyclerview)

    implementation ("androidx.recyclerview:recyclerview:1.3.2")
    implementation ("androidx.wear:wear:1.2.0")
    implementation ("androidx.wear:wear-input:1.1.0")
    implementation ("androidx.wear:wear-ongoing:1.0.0")

    implementation ("com.google.android.support:wearable:2.9.0")
    implementation ("com.google.android.gms:play-services-wearable:17.1.0")
    compileOnly ("com.google.android.wearable:wearable:2.9.0")
}