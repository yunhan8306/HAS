plugins {
    id("myStash.android.library")
    id("myStash.android.library.compose")
    id("myStash.android.hilt")
}

android {
    namespace = "com.myStash.android.feature.search"
}

dependencies {
    implementation(projects.core.designSystem)
    implementation(projects.core.model)
    implementation(projects.core.data)
    implementation(projects.common.compose)
    implementation(projects.common.util)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.lifecycle.runtime)
    implementation(libs.androidx.compose.lifecycle.viewmodel)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.foundation)
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}