plugins {
    id("application")
}

dependencies {
    api(project(":converter"))
}

application {
    mainClass.set("org.geysermc.pack.converter.boostrap.Main")
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    from("src/main/java/resources") {
        include("*")
    }

    archiveFileName.set("PackConverter.jar")
    archiveClassifier.set("")
}