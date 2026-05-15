plugins {
    id("starter.android.library")
    id("starter.android.hilt")
}

android {
    namespace = "com.starter.core.network"
}

dependencies {
    implementation(project(":core:model"))

    // Retrofit + OkHttp
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    // Coroutines
    implementation(libs.coroutines.core)
}
