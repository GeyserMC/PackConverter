import java.net.URI

plugins {
    `maven-publish` apply true
    id("com.github.johnrengelman.shadow") apply false
}

publishing {
    repositories {
        maven {
            name = "geysermc"
            url = URI.create(
                when {
                    version.toString().endsWith("-SNAPSHOT") ->
                        "https://repo.opencollab.dev/maven-snapshots"
                    else ->
                        "https://repo.opencollab.dev/maven-releases"
                }
            )
            credentials(PasswordCredentials::class.java)
        }
    }

    publications {
        register("publish", MavenPublication::class) {
            from(project.components["java"])

            // skip shadow jar from publishing. Workaround for https://github.com/johnrengelman/shadow/issues/651
            val javaComponent = project.components["java"] as AdhocComponentWithVariants
            javaComponent.withVariantsFromConfiguration(configurations["shadowRuntimeElements"]) { skip() }
        }
    }
}