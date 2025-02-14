plugins {
    id("has.android.library")
    id("has.android.hilt")
}

android {
    namespace = "com.has.android.core.data_base"
}

dependencies {
    implementation(projects.core.model)

    implementation(libs.androidx.room)
    implementation(libs.moshi)
    kapt(libs.moshi.codegen)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.paging)
    kapt(libs.androidx.room.compiler)
}