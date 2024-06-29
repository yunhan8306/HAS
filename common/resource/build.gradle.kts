plugins {
    id("myStash.android.library")
}

android {
    namespace = "com.myStash.android.common.resource"
}


dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
}