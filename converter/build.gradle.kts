dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.0")
    implementation("com.twelvemonkeys.imageio:imageio-tga:3.5")
    implementation("com.nukkitx.fastutil:fastutil-int-object-maps:8.5.3")
    implementation("net.kyori:adventure-api:4.13.1")
    implementation("net.kyori:adventure-text-serializer-gson:4.13.1")
    implementation("net.kyori:adventure-text-serializer-legacy:4.13.1")
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    from("src/main/java/resources") {
        include("*")
    }

    archiveClassifier.set("")
}