plugins {
    `maven-publish` apply true
    id("com.github.johnrengelman.shadow") apply false
}

publishing {
    repositories {
        val repoName = if (version.toString().endsWith("SNAPSHOT")) "maven-snapshots" else "maven-releases"
        maven("https://repo.opencollab.dev/${repoName}/") {
            credentials.username = System.getenv("OPENCOLLAB_USERNAME")
            credentials.password = System.getenv("OPENCOLLAB_PASSWORD")
            name = "opencollab"
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