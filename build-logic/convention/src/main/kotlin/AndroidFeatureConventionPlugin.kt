import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("starter.android.library")
            pluginManager.apply("starter.android.compose")
            pluginManager.apply("starter.android.hilt")

            dependencies {
                add("implementation", project(":core:model"))
                add("implementation", project(":core:domain"))

                add("testImplementation", project(":core:testing"))
            }
        }
    }
}
