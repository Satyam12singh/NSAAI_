import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.nsaai"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.nsaai"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    val localProperties= Properties()
    val localPropertiesFile=rootProject.file("secret.properties")
    if(localPropertiesFile.exists() && localPropertiesFile.isFile){
        localProperties.load(FileInputStream(localPropertiesFile))
    }


    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "API_KEY", "\"${localProperties.getProperty("API_KEY")}\"")
            buildConfigField("String", "api_key_normal", "\"${localProperties.getProperty("api_key_normal")}\"")
            buildConfigField("String", "googleclientid", "\"${localProperties.getProperty("googleclientid")}\"")
        }
        debug {
            buildConfigField("String", "API_KEY", "\"${localProperties.getProperty("API_KEY")}\"")
            buildConfigField("String", "api_key_normal", "\"${localProperties.getProperty("api_key_normal")}\"")
            buildConfigField("String", "googleclientid", "\"${localProperties.getProperty("googleclientid")}\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig=true
    }
}

dependencies {

    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.31.2-alpha")

    
    implementation("androidx.compose.material:material-icons-extended:1.7.6")


    // Network calls
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
// JSON to Kotlin object mapping
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
// Image loading
    implementation("io.coil-kt:coil-compose:2.4.0")

    implementation ("com.facebook.android:facebook-login:15.1.0")
    implementation ("com.google.firebase:firebase-auth-ktx")


    implementation("com.google.firebase:firebase-auth-ktx:23.1.0")
    implementation("com.google.android.gms:play-services-auth:20.4.1")

    implementation("androidx.navigation:navigation-compose:2.8.5")
    implementation("androidx.compose.ui:ui:1.6.0-alpha08")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.0-alpha08")
    implementation("androidx.compose.material:material:1.6.0-alpha08")
    implementation(libs.googleid)

    val voyagerversion="1.0.0"
    implementation("cafe.adriel.voyager:voyager-navigator:$voyagerversion")
    implementation("cafe.adriel.voyager:voyager-transitions:$voyagerversion")
    implementation("cafe.adriel.voyager:voyager-tab-navigator:$voyagerversion")

    //    System ui controller

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.27.0")
//    Extended Icons
    implementation("androidx.compose.material:material-icons-extended:1.7.6")

    implementation("com.google.android.gms:play-services-auth:20.4.1")
//    implementation("androidx.credentials:credentials:1.5.0")
//    implementation("androidx.credentials:credentials-play-services-auth:1.5.0")
//    implementation("com.google.android.libraries.identity.googleid:googleid:1.5.0")
    implementation("com.google.firebase:firebase-auth-ktx:23.1.0")
    implementation ("com.google.accompanist:accompanist-swiperefresh:0.28.0")

    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:chromecast-sender:0.28")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}