plugins {
    id("myStash.android.library")
    id("kotlin-parcelize")
}

android {
    namespace = "com.myStash.android.core.model"
}

dependencies {
    implementation(project(":common:util"))
}