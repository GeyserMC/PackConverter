dependencies {
    api(project(":pack-schema-api"))
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("commons-io:commons-io:2.11.0")
    implementation("com.twelvemonkeys.imageio:imageio-tga:3.9.4")
    implementation("com.nukkitx.fastutil:fastutil-int-object-maps:8.5.3")
    implementation("net.kyori:adventure-api:4.14.0")
    implementation("net.kyori:adventure-text-serializer-gson:4.14.0")
    implementation("net.kyori:adventure-text-serializer-legacy:4.14.0")
    implementation("team.unnamed:creative-api:1.7.0")
    implementation("team.unnamed:creative-serializer-minecraft:1.7.0")

    compileOnly("com.google.auto.service:auto-service:1.0.1")
    annotationProcessor("com.google.auto.service:auto-service:1.0.1")
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    from("src/main/java/resources") {
        include("*")
    }

    archiveClassifier.set("")
}