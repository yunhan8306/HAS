plugins {
    id("has.android.library")
    id("has.android.hilt")
}

android {
    namespace = "com.has.android.data"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.domain)

    implementation(libs.javax)
    implementation(libs.data.store)
    implementation(libs.data.store.core)

    implementation(libs.androidx.room)
    implementation(libs.moshi)
    kapt(libs.moshi.codegen)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.paging)
    kapt(libs.androidx.room.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
