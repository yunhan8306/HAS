plugins {
    id("myStash.android.library")
    id("myStash.android.library.compose")
    id("myStash.android.hilt")
}

android {
    namespace = "com.myStash.feature.essential"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.designSystem)
    implementation(projects.core.data)
    implementation(projects.common.compose)
    implementation(projects.common.util)
    implementation(projects.feature.navigation)

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
    implementation(libs.system.ui.controller)

    implementation(libs.androidx.paging)
    implementation(libs.androidx.paging.compose)
}