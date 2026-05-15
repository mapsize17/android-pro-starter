plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(platform(libs.android.gradle.plugin))
    implementation(libs.kotlin.gradle.plugin)
}
