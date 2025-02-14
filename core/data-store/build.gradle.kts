plugins {
    id("has.android.library")
    id("has.android.hilt")
}

android {
    namespace = "com.has.android.core.data_store"
}

dependencies {
    implementation(libs.javax)
    implementation(libs.data.store)
    implementation(libs.data.store.core)
}