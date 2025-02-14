plugins {
    id("has.android.library")
    id("has.android.compose")
    id("has.android.library.compose")
    id("has.android.hilt")
}

android {
    namespace = "com.has.android.feature.feed"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.designSystem)
    implementation(projects.core.data)
    implementation(projects.core.di)
    implementation(projects.common.compose)
    implementation(projects.common.util)
    implementation(projects.common.resource)
    implementation(projects.feature.navigation)
    implementation(projects.feature.gallery)
    implementation(projects.feature.item)
    implementation(projects.feature.gender)
    implementation(projects.feature.search)

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
    implementation(libs.balloon)

    implementation(libs.androidx.paging)
    implementation(libs.androidx.paging.compose)
}