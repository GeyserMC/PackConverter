plugins {
    `java-library`
    application
    id("com.github.johnrengelman.shadow") apply true
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
    implementation("com.twelvemonkeys.imageio:imageio-tga:3.9.4")
    implementation("com.formdev:flatlaf:3.6")
    implementation("com.formdev:flatlaf-intellij-themes:3.6")
}

application {
    mainClass.set("org.geysermc.pack.converter.bootstrap.Main")
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("PackConverter.jar")
}