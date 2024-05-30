plugins {
    id("myStash.android.library")
    id("myStash.android.hilt")
}

android {
    namespace = "com.myStash.android.core.di"
}

dependencies {
    implementation(projects.core.dataBase)
    implementation(projects.core.dataStore)
    implementation(projects.core.data)

    implementation(libs.androidx.room)
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)

    implementation(libs.okhttp)
    implementation(libs.data.store)
    implementation(libs.retrofit.logging)
    implementation(libs.moshi)
    kapt(libs.moshi.codegen)
}