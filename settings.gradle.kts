pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "AndroidStarter"
include(":app")
include(":core:ui")
include(":core:network")
include(":core:database")
include(":core:datastore")
include(":core:model")
include(":core:testing")
include(":core:domain")
include(":feature:auth")
include(":feature:home")
include(":feature:settings")
include(":data:repository")
