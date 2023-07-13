plugins {
    id("java")
    id("java-library")
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "7.1.0"
    id("io.freefair.lombok") version "6.3.0" apply false
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")
    apply(plugin = "com.github.johnrengelman.shadow")
    apply(plugin = "io.freefair.lombok")

    repositories {
        mavenLocal()
        mavenCentral()

        gradlePluginPortal()

        // Geyser, Floodgate, Cumulus etc.
        maven("https://repo.opencollab.dev/main")
        maven("https://repo.opencollab.dev/maven-snapshots")

        // Java pack library
        maven("https://repo.unnamed.team/repository/unnamed-public/")
    }

    group = "org.geysermc"
    version = "3.0-SNAPSHOT"

    java.sourceCompatibility = JavaVersion.VERSION_17
    java.targetCompatibility = JavaVersion.VERSION_17

    tasks.jar {
        archiveClassifier.set("unshaded")
    }

    tasks.named("build") {
        dependsOn(tasks.shadowJar)
    }

    publishing {
        publications.create<MavenPublication>("library") {
            artifact(tasks.shadowJar)
        }
    }
}