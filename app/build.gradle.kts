plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.dmoneyekyc"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.dmoneyekyc"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildFeatures {
            buildConfig = true
        }
    }

    buildTypes {

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            applicationIdSuffix = ".pr"
            signingConfig = signingConfigs.getByName("debug")
        }
        debug{
            applicationIdSuffix = ".db"
//            buildConfigField 'String', 'PRODUCT_CODE', '"DM"'
            buildConfigField("String","PRODUCT_CODE", "\"DM\"")
//            buildConfigField 'String', 'SERVICE_CONTROLLER', '"E8xlkWsSjZKBZ8yQ6VjaQIUM9tUfo/bPdrOy+BATiwc="'
            buildConfigField("String","SERVICE_CONTROLLER",
                "\"E8xlkWsSjZKBZ8yQ6VjaQIUM9tUfo/bPdrOy+BATiwc=\""
            )
            versionNameSuffix ="-TEST-05-05"
//            buildConfigField 'boolean', 'OAUTH_ON', 'true'
            buildConfigField("boolean","OAUTH_ON","true")
//            buildConfigField 'String', 'BASE_URL', '"https://test.dmoney.com.bd:3035/DmoneyPlatform/"'

            buildConfigField("String","BASE_URL",
                "\"https://test.dmoney.com.bd:3035/DmoneyPlatform/\""
            )

        }

        create("dtest") {
            applicationIdSuffix = ".ts"
            signingConfig = signingConfigs.getByName("debug")
        }


    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.play.services.mlkit.face.detection)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("androidx.compose.material:material-icons-extended:1.6.8")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("com.google.dagger:hilt-android:2.49")
    kapt("com.google.dagger:hilt-compiler:2.48")
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.30.1")

//    network dependency
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation ("com.squareup.okhttp3:logging-interceptor:3.9.1")

    implementation ("androidx.camera:camera-camera2:1.3.4")
    implementation ("androidx.camera:camera-lifecycle:1.3.4")
    implementation (libs.androidx.camera.view.v100alpha22)
    implementation ("androidx.camera:camera-extensions:1.3.4")
    implementation("androidx.core:core-splashscreen:1.0.0")
    implementation ("com.google.accompanist:accompanist-navigation-animation:0.35.1-alpha")
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.30.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")

    implementation ("com.google.mlkit:text-recognition:16.0.0")
}