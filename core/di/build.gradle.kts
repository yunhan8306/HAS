plugins {
    id("has.android.library")
    id("has.android.hilt")
}

android {
    namespace = "com.has.android.core.di"
}

dependencies {
    implementation(projects.domain)
    implementation(projects.data)

    implementation(libs.androidx.room)
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)

    implementation(libs.okhttp)
    implementation(libs.data.store)
    implementation(libs.retrofit.logging)
    implementation(libs.moshi)
    kapt(libs.moshi.codegen)
}