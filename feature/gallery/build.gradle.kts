plugins {
    id("myStash.android.library")
    id("myStash.android.library.compose")
    id("myStash.android.hilt")
}

android {
    namespace = "com.myStash.android.feature.gallery"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.designSystem)
    implementation(projects.core.di)
    implementation(projects.core.data)
    implementation(projects.common.compose)
    implementation(projects.common.util)
    implementation(projects.common.resource)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.lifecycle.runtime)
    implementation(libs.androidx.compose.lifecycle.viewmodel)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material.compose)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.coil)
    implementation(libs.orbit.compose)
    implementation(libs.orbit.viewmodel)

    implementation(libs.androidx.compose.hilt.navigation)
}