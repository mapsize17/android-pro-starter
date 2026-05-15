plugins {
    id("starter.android.library")
    id("starter.android.hilt")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.starter.core.database"
}

dependencies {
    implementation(project(":core:model"))
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
}
