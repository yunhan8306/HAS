plugins {
    id("has.android.library")
    id("has.android.compose")
    id("has.android.library.compose")
    id("has.android.hilt")
}

android {
    namespace = "com.has.android.feature.manage"
}

dependencies {
    implementation(projects.core.designSystem)
    implementation(projects.core.model)
    implementation(projects.domain)
    implementation(projects.common.resource)
    implementation(projects.common.compose)
    implementation(projects.common.util)
    implementation(projects.feature.gallery)
    implementation(projects.feature.search)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.lifecycle.runtime)
    implementation(libs.androidx.compose.lifecycle.viewmodel)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material.compose)
    implementation(libs.coil)
    implementation(libs.system.ui.controller)
    implementation(libs.androidx.compose.navigation)
    implementation(libs.androidx.compose.hilt.navigation)
    implementation(libs.orbit.compose)
    implementation(libs.orbit.viewmodel)
}