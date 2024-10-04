
import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
//    override fun apply(target: Project) {
//        with(target) {
//            pluginManager.apply("com.android.application")
//            val extension = extensions.getByType<ApplicationExtension>()
//            configureAndroidCompose(extension)
//        }
//    }

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")
            extensions.getByType<BaseExtension>().apply {
                buildFeatures.apply {
                    compose = true
                }
            }
            extensions.configure<ComposeCompilerGradlePluginExtension> {
                enableStrongSkippingMode.set(true)
                includeSourceInformation.set(true)
            }
        }
    }
}