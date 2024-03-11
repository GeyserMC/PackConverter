plugins {
    `java-library`
    application
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

sourceSets {
    main {
        resources {
            srcDir("src/main/java/resources")
        }
    }
}

dependencies {
    api(project(":converter"))
}

application {
    mainClass.set("org.geysermc.pack.converter.bootstrap.Main")
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveClassifier.set("all")
    archiveFileName.set("PackConverter.jar")
}