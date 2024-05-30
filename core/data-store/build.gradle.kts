plugins {
    id("myStash.android.library")
    id("myStash.android.hilt")
}

android {
    namespace = "com.myStash.android.core.data_store"
}

dependencies {
    implementation(libs.javax)
    implementation(libs.data.store)
    implementation(libs.data.store.core)
}