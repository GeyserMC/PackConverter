plugins {
    id("io.freefair.lombok") version "8.6"
}

sourceSets {
    main {
        resources {
            srcDir("src/main/java/resources")
        }
    }
    test {
        resources {
            srcDir("src/test/java/resources")
        }
    }
}

dependencies {
    api(project(":pack-schema-api"))
    compileOnly(project(":bedrock-pack-schema")) // Is provided by pack-schema-api for consumers, but not for us during compile time
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("commons-io:commons-io:2.11.0")
    implementation("com.twelvemonkeys.imageio:imageio-tga:3.9.4")
    implementation("com.nukkitx.fastutil:fastutil-int-object-maps:8.5.3")
    api("net.kyori:adventure-api:4.14.0")
    api("net.kyori:adventure-text-serializer-gson:4.14.0")
    api("net.kyori:adventure-text-serializer-legacy:4.14.0")
    api("team.unnamed:creative-api:1.14.4")
    api("team.unnamed:creative-serializer-minecraft:1.14.4")

    compileOnly("com.google.auto.service:auto-service:1.0.1")
    annotationProcessor("com.google.auto.service:auto-service:1.0.1")

    // Test dependencies — required by tests added in PR #72.
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.2")
}

java {
    withSourcesJar()
}

tasks.withType<Test> {
    useJUnitPlatform()
}