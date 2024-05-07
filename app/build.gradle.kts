import com.myStash.convention.VersionConstants
import java.io.FileInputStream
import java.util.Properties

plugins {
    id("myStash.android.application")
    id("myStash.android.application.compose")
    id("myStash.android.hilt")
    id("org.jetbrains.kotlin.android")
}

android {
    defaultConfig {
        applicationId = "com.myStash.android"
        versionCode = VersionConstants.VERSION_CODE
        versionName = VersionConstants.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    signingConfigs {
        val releaseSigningConfig by creating {
            val properties = Properties().apply {
                load(FileInputStream("${rootDir}/local.properties"))
            }
            storeFile = file("${rootDir}/${properties["keystore"]}")
            keyAlias = "${properties["key_alias"]}"
            keyPassword = "${properties["key_password"]}"
            storePassword = "${properties["store_password"]}"
            enableV1Signing = true
            enableV2Signing = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("releaseSigningConfig")
        }
        debug {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("releaseSigningConfig")
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    namespace = "com.myStash.android"
}

dependencies {

    implementation(projects.feature.navigation)
    implementation(projects.core.data)
    implementation(projects.core.model)
    implementation(projects.core.di)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.lifecycle.runtime)
    implementation(libs.androidx.compose.lifecycle.viewmodel)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.navigation)
    implementation(libs.androidx.compose.hilt.navigation)
    implementation(libs.navigation.animation)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material.compose)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.coil)
    implementation(libs.orbit.compose)
    implementation(libs.orbit.viewmodel)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}