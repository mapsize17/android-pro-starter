plugins {
    id("starter.android.library")
    id("starter.android.hilt")
}

android {
    namespace = "com.starter.data.repository"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:network"))
    implementation(project(":core:database"))
    implementation(project(":core:datastore"))
}
