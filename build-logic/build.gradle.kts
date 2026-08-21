plugins {
    `kotlin-dsl`
}

dependencies {
    implementation("com.gradleup.shadow:com.gradleup.shadow.gradle.plugin:8.3.11")
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}