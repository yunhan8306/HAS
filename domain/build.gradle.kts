plugins {
    id("has.android.library")
    id("has.android.hilt")
}

android {
    namespace = "com.has.android.domain"
}


dependencies {
    implementation(projects.core.model)

    implementation(libs.coroutine.core)
    implementation(libs.androidx.room.paging)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}