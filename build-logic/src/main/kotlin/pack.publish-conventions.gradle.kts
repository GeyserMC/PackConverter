plugins {
    `maven-publish`
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
}