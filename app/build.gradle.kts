plugins {
    id("com.android.application")
}

android {
    namespace = "com.thuanht.eatez"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.thuanht.eatez"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "Beta 1.0"

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
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
//    implementation("com.airbnb.android:lottie:3.4.1")
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.61")
    implementation("com.etebarian:meow-bottom-navigation:1.0.2")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.0.0")
    implementation("com.makeramen:roundedimageview:2.3.0")
    implementation("com.facebook.shimmer:shimmer:0.4.0")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.github.amarjain07:StickyScrollView:1.0.2")
}