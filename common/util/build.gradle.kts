plugins {
    id("myStash.android.library")
    id("myStash.android.compose")
    id("myStash.android.hilt")
}

android {
    namespace = "com.myStash.android.common.util"
}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.lifecycle.runtime)
    implementation(libs.androidx.compose.lifecycle.viewmodel)
    implementation(libs.androidx.compose.runtime)

    implementation(libs.appcompat)
    implementation(libs.material)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
}