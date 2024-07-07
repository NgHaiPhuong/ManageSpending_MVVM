plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlin-kapt")
}

kapt {
    correctErrorTypes = true
}

android {
    namespace = "com.example.managespending"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.managespending"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    dataBinding {
        enable = true
    }
    viewBinding {
        enable = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(libs.androidx.activity)
    implementation(libs.androidx.ui.desktop)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    //MPChart
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")


    // Add the annotation processor if you are using Epoxy's annotations (recommended)
    implementation("com.airbnb.android:epoxy:5.1.4")
    // Add the annotation processor if you are using Epoxy's annotations (recommended)
    kapt("com.airbnb.android:epoxy-processor:5.1.4")
    implementation("com.airbnb.android:epoxy-databinding:5.1.4")

    // Layout error alignment library for horizontal epoxy model
    implementation("com.github.rubensousa:gravitysnaphelper:2.2.2")

    // Layout error alignment library for horizontal epoxy model
    implementation("com.github.rubensousa:gravitysnaphelper:2.2.2")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // scalable dp, sp library
    implementation("com.intuit.sdp:sdp-android:1.1.1")

    // lib for lottie animation
    implementation("com.airbnb.android:lottie:6.4.0")

    implementation ("com.android.support:appcompat-v7:27.1.0")
    implementation ("com.android.support:design:27.1.0")

    //CardView
    implementation("androidx.cardview:cardview:1.0.0")
    
    //RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // room
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt( "android.arch.persistence.room:compiler:1.0.0")

    //design
    implementation ("com.android.support:design:26.1.0")

    // Meow Bottom Nav
    implementation ("com.etebarian:meow-bottom-navigation:1.2.0")

    //Circle Image
    implementation ("de.hdodenhof:circleimageview:3.1.0")

    //viewmodel, livedata
    val lifecycle_version = "2.8.1"

    implementation("com.google.android.material:material:1.1.0-beta01")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")

    // Material Components library
    implementation ("com.google.android.material:material:1.9.0")
}