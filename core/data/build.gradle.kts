plugins {
    id("myStash.android.library")
    id("myStash.android.hilt")
}

android {
    namespace = "com.myStash.android.core.data"
}


dependencies {
    implementation(projects.core.dataBase)
    implementation(projects.core.dataStore)
    implementation(projects.core.model)

    implementation(libs.coroutine.core)
    implementation(libs.androidx.room.paging)
}