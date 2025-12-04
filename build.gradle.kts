import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import org.gradle.kotlin.dsl.withType

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.detekt)
}

dependencies {
    detektPlugins(libs.detekt.formatting)
    detektPlugins(libs.detekt.compose)
}

detekt {
    source.from(files(rootProject.rootDir))
    parallel = true
}

tasks.withType<Detekt>().configureEach {
    autoCorrect = true

    // exclude resources and build folder
    excludeFolders()

    reports {
        xml.required.set(true)
        txt.required.set(true)
    }

    if (File("$projectDir/detekt-baseline.xml").exists()) {
        baseline = file("$projectDir/detekt-baseline.xml")
    }
}

tasks.withType<DetektCreateBaselineTask>().configureEach {
    excludeFolders()
}

private fun SourceTask.excludeFolders() {
    exclude("**/resources/**")
    exclude("**/.kotlin/**")
    exclude("**/build/**")
    exclude("**/.idea/**")
    exclude("**/.gradle/**")
    exclude("**/gradle/**")
    exclude("**/iosApp/**")
}
