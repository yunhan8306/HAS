plugins {
    id("myStash.android.library")
    id("myStash.android.hilt")
}

android {
    namespace = "com.myStash.android.core.data_base"
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