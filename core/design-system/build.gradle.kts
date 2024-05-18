plugins {
    id("myStash.android.library")
    id("myStash.android.library.compose")
    id("myStash.android.hilt")
}

android {
    namespace = "com.myStash.core.design_system"
}

dependencies {
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
    implementation(libs.navigation.animation)
    implementation(libs.androidx.paging)
    implementation(libs.androidx.paging.compose)
}