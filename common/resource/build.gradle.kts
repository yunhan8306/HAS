plugins {
    id("has.android.library")
}

android {
    namespace = "com.has.android.common.resource"
}


dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
}