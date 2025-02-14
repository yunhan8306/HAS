plugins {
    id("has.android.library")
    id("has.android.hilt")
}

android {
    namespace = "com.has.android.di"
}

dependencies {
    implementation(projects.domain)
    implementation(projects.data)

    implementation(libs.androidx.room)
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)

    implementation(libs.data.store)

    implementation(libs.moshi)
    kapt(libs.moshi.codegen)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}