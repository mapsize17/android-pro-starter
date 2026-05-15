plugins {
    id("starter.android.library")
}

android {
    namespace = "com.starter.core.testing"
}

dependencies {
    implementation(libs.junit)
    implementation(libs.truth)
    implementation(libs.coroutines.test)
    implementation(libs.turbine)
    implementation(libs.compose.ui.test.junit4)
    implementation(libs.lifecycle.runtime.testing)
    implementation(libs.okhttp.mockwebserver)
}
