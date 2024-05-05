plugins {
    id("myStash.android.library")
    id("myStash.android.hilt")
}

android {
    namespace = "com.myStash.core.data"
}


dependencies {
    implementation(projects.core.dataBase)
    implementation(projects.core.model)

    implementation(libs.coroutine.core)
    implementation(libs.androidx.room.paging)
}