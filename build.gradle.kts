// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kotlin.serialization)
    id("io.gitlab.arturbosch.detekt") version "1.22.0" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.3.1" apply false
//    id("com.google.gms.google-services") version "4.3.15" apply false
}


subprojects {
    plugins.apply("org.jetbrains.kotlin.plugin.serialization")
}