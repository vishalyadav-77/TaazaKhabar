plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.example.taazakhabar"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.taazakhabar"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.glide)
    implementation(libs.swiperefreshlayout)
    implementation(libs.paging)
    implementation(libs.rooms)
    annotationProcessor(libs.roomcompiler)

}