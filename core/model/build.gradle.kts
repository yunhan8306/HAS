plugins {
    id("has.android.library")
    id("kotlin-parcelize")
}

android {
    namespace = "com.has.android.core.model"
}

dependencies {
    implementation(project(":common:util"))
}