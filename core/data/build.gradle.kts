plugins {
    id("has.android.library")
    id("has.android.hilt")
}

android {
    namespace = "com.has.android.core.data"
}


dependencies {
    implementation(projects.core.dataBase)
    implementation(projects.core.dataStore)
    implementation(projects.core.model)

    implementation(libs.coroutine.core)
    implementation(libs.androidx.room.paging)
}