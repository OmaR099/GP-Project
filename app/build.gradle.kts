
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    kotlin("kapt") version "1.9.22"
    id("com.google.devtools.ksp")

}

android {
    namespace = "com.example.loginscreen"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.loginscreen"
        minSdk = 28
        targetSdk = 33
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
        viewBinding = true
        mlModelBinding = true
        compose = true
    }

    kapt {
        generateStubs = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }

    
}
apply(plugin = "kotlin-kapt")
dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-auth:23.0.0")
    implementation("com.google.android.gms:play-services-auth:21.1.1")
    implementation("com.google.firebase:firebase-database:21.0.0")
    implementation ("com.facebook.android:facebook-android-sdk:latest.release")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.google.firebase:firebase-storage")
    implementation("com.google.firebase:firebase-core:21.1.1")
    implementation("com.android.car.ui:car-ui-lib:2.6.0")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib:1.9.22")
    implementation("org.tensorflow:tensorflow-lite-gpu:2.5.0")
    implementation("androidx.compose.material3:material3-android:1.2.1")


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation ("de.hdodenhof:circleimageview:3.1.0")

    implementation("org.tensorflow:tensorflow-lite-support:0.2.0")
    implementation("org.tensorflow:tensorflow-lite-metadata:0.2.0")

    implementation ("androidx.camera:camera-view:1.3.3")

    //scalable unit size
    implementation ("com.intuit.sdp:sdp-android:1.0.6")

    //scalable unit text size
    implementation ("com.intuit.ssp:ssp-android:1.0.6")
//    room Database
    implementation ("androidx.room:room-runtime:2.6.1")
    implementation ("androidx.room:room-ktx:2.6.1")
    implementation ("com.makeramen:roundedimageview:2.3.0")
    kapt ("androidx.room:room-compiler:2.6.1")

    //crop image library

//    implementation ("com.theartofdev.edmodo:android-image-cropper:2.8.0")

    //easy permission

    implementation ("pub.devrel:easypermissions:3.0.0")

    //coroutines core
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.28")

    implementation ("com.mikhaellopez:circularprogressbar:3.1.0")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose: 1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")


    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    implementation("io.coil-kt:coil-compose:2.4.0")

    implementation("androidx.compose.material:material-icons-extended:1.6.7")

    implementation("com.google.ai.client.generativeai:generativeai:0.5.0")

}

