plugins {
    id("io.freefair.lombok") version "8.14.4"
}

lombok {
    version = "1.18.46"
}

sourceSets {
    main {
        resources {
            srcDir("src/main/java/resources")
        }
    }
}

dependencies {
    api(project(":pack-schema-api"))
    compileOnly(project(":bedrock-pack-schema")) // Is provided by pack-schema-api for consumers, but not for us during compile time
    testImplementation(project(":bedrock-pack-schema")) // compileOnly is absent from the test classpath
    implementation("com.google.code.gson:gson:2.14.0")
    implementation("commons-io:commons-io:2.22.0")
    implementation("com.twelvemonkeys.imageio:imageio-tga:3.14.0")
    implementation("com.nukkitx.fastutil:fastutil-int-object-maps:8.5.3")
    api("net.kyori:adventure-api:4.14.0")
    api("net.kyori:adventure-text-serializer-gson:4.14.0")
    api("net.kyori:adventure-text-serializer-legacy:4.14.0")
    api("team.unnamed:creative-api:1.13.6")
    api("team.unnamed:creative-serializer-minecraft:1.13.6")

    compileOnly("com.google.auto.service:auto-service:1.0.1")
    annotationProcessor("com.google.auto.service:auto-service:1.0.1")

    testImplementation("org.junit.jupiter:junit-jupiter:6.1.3")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher") // Gradle 9 no longer loads the launcher implicitly
}

tasks.test {
    useJUnitPlatform()
    // Allow passing the mod jar for the live Tabula reflection test.
    val modjar = System.getProperty("tabula.modjar")
    if (modjar != null) {
        systemProperty("tabula.modjar", modjar)
    }
}

java {
    withSourcesJar()
}
