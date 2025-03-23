plugins {
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.kotiln.serializer.plugin)

}
android {
    namespace = "com.satwik.transfertoinr"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.satwik.transfertoinr"
        minSdk = 24
        targetSdk = 35
        versionCode = 2
        versionName = "1.1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }


    // Excludes heavy TF *.so files from the APK
    packagingOptions {
        exclude("lib/**/libtensorflowlite_jni.so")
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

    //Splash Screen API
    implementation(libs.splash.screen.api)

    //Coil
    implementation(libs.coil)

    //Coroutines
    implementation(libs.bundles.coroutines)

    //Ktor
    implementation(libs.bundles.ktor)

    implementation("io.ktor:ktor-client-okhttp:3.0.3")


    //Supabase
    implementation(libs.bundles.supabase)

    //Accompanist
    implementation(libs.accompanist)

    implementation ("com.google.accompanist:accompanist-navigation-animation:0.32.0")

    //Accompanist Pager
    implementation ("com.google.accompanist:accompanist-pager:0.22.0-rc")

    val koin = "4.0.2"

    implementation ("io.insert-koin:koin-core:4.0.2")
    implementation("io.insert-koin:koin-android:4.0.2")
    implementation("io.insert-koin:koin-androidx-compose:4.0.2")
    implementation("io.insert-koin:koin-androidx-compose-navigation:4.0.2")
    implementation("io.insert-koin:koin-androidx-navigation:4.0.2")
    implementation ("com.airbnb.android:lottie-compose:4.0.0")








    implementation(libs.firebase.messaging)

    val sumsubSDK = "1.34.1"
    // SumSub core
    implementation("com.sumsub.sns:idensic-mobile-sdk:$sumsubSDK")

    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}