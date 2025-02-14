package com.has.convention

import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = VersionConstants.TARGET_SDK

        defaultConfig {
            minSdk = VersionConstants.MIN_SDK
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
            isCoreLibraryDesugaringEnabled = true
        }
    }

    configureKotlin()

    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    dependencies {
        add("coreLibraryDesugaring", libs.findLibrary("android.desugarJdkLibs").get())
    }
}

private fun Project.configureKotlin() {
    tasks.withType(KotlinCompile::class.java).configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)

            freeCompilerArgs.addAll(
                listOf(
                    "-opt-in=kotlin.RequiresOptIn",
                    "-opt-in=androidx.compose.animation.ExperimentalAnimationApi",
                    "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
                    "-opt-in=kotlinx.coroutines.FlowPreview",
                    "-opt-in=androidx.compose.foundation.layout.ExperimentalLayoutApi",
                    "-opt-in=androidx.compose.foundation.layout.ExperimentalMaterialApi",
                )
            )
        }
    }
}

internal fun LibraryExtension.configureBuildConfig() {
//    buildTypes {
//        release {
//            buildConfigField("String", "VERSION_NAME", "\"${VersionConstants.VERSION_NAME}\"")
//            buildConfigField("Integer", "VERSION_CODE", "${VersionConstants.VERSION_CODE}")
//        }
//        debug {
//            buildConfigField("String", "VERSION_NAME", "\"${VersionConstants.VERSION_NAME}\"")
//            buildConfigField("Integer", "VERSION_CODE", "${VersionConstants.VERSION_CODE}")
//        }
//    }
}