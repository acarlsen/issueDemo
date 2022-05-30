plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "acarlsen.issuedemo"
        minSdk = 23
        targetSdk = 32
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0-beta01"
    }
}

dependencies {

    val kotlin = "1.6.21"
    val accompanist = "0.24.6-alpha"
    val compose = "1.2.0-beta01"

    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlin")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1")

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("androidx.navigation:navigation-compose:2.5.0-rc01")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
    implementation("androidx.activity:activity-ktx:1.4.0")
    implementation("androidx.activity:activity-compose:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation("androidx.compose.material3:material3:1.0.0-alpha12")
    implementation("androidx.compose.material3:material3-window-size-class:1.0.0-alpha12")

    implementation("androidx.compose.ui:ui:$compose")
    implementation("androidx.compose.material:material:$compose")
    implementation("androidx.compose.ui:ui-tooling:$compose")
    implementation("androidx.compose.ui:ui-tooling-preview:$compose")
    implementation("androidx.compose.ui:ui-util:$compose")
    implementation("androidx.compose.ui:ui-viewbinding:$compose")
    implementation("androidx.compose.foundation:foundation:$compose")
    implementation("androidx.compose.runtime:runtime-livedata:$compose")

    implementation("com.google.accompanist:accompanist-insets:$accompanist")
    implementation("com.google.accompanist:accompanist-pager:$accompanist")
    implementation("com.google.accompanist:accompanist-flowlayout:$accompanist")
    implementation("com.google.accompanist:accompanist-systemuicontroller:$accompanist")
    implementation("com.google.accompanist:accompanist-placeholder-material:$accompanist")
    implementation("com.google.accompanist:accompanist-navigation-animation:$accompanist")
    implementation("com.google.accompanist:accompanist-permissions:$accompanist")
    implementation("com.google.accompanist:accompanist-webview:$accompanist")

    implementation("io.coil-kt:coil-compose:2.1.0")

    debugImplementation("androidx.compose.ui:ui-test-manifest:$compose")

    testImplementation("junit:junit:4.13.2")
    
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$compose")
}