plugins {
    id("starter.android.library")
}

android {
    namespace = "com.starter.core.domain"
}

dependencies {
    implementation(project(":core:model"))
}
