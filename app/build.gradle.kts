plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    signingConfigs {
        create("config_thuanht") {
            storeFile = file("/home/thuanht/Desktop/eatez_keystore_v2.jks")
            storePassword = "123456"
            keyAlias = "key01"
            keyPassword = "123456"
        }
    }
    namespace = "com.thuanht.eatez"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.thuanht.eatez"
        minSdk = 24
        targetSdk = 34
        versionCode = 5
        versionName = "1.0.5"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("config_thuanht")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity:1.8.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    // UI Library
    implementation("com.airbnb.android:lottie:6.4.0")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.9.23")
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("com.etebarian:meow-bottom-navigation:1.0.2")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("com.makeramen:roundedimageview:2.3.0")
    implementation("com.facebook.shimmer:shimmer:0.5.0")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.chauthai.swipereveallayout:swipe-reveal-layout:1.4.0")
    implementation("com.saadahmedev.popup-dialog:popup-dialog:2.0.0")
    implementation("com.github.ome450901:SimpleRatingBar:1.5.1");
    implementation("io.github.nikartm:fit-button:2.0.0");
    implementation("com.github.clans:fab:1.6.4");
    implementation ("com.github.dangiashish:StyledCardView:1.0.0")
    implementation("com.github.leandroborgesferreira:loading-button-android:2.3.0")
    implementation("io.github.ParkSangGwon:tedpermission-normal:3.3.0")
    // API & Networking
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    // RoomDatabase
    implementation ("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    // RxAndroid - RxJava
    implementation ("io.reactivex.rxjava3:rxandroid:3.0.2")
    implementation ("io.reactivex.rxjava3:rxjava:3.1.8")
    implementation("com.github.akarnokd:rxjava3-retrofit-adapter:3.0.0")
    // Firebase
    implementation("com.google.firebase:firebase-analytics")
    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-messaging")
    implementation ("com.google.android.gms:play-services-auth:21.1.1")
    // Google Location Service
    implementation("com.google.android.gms:play-services-location:21.2.0")
    // Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    implementation ("com.google.android.gms:play-services-ads:23.1.0")
    implementation ("com.facebook.android:facebook-android-sdk:17.0.0")
}