plugins {
    id("pack.base-conventions")
    id("pack.publish-conventions")
}

subprojects{
    plugins.apply("pack.base-conventions")
    plugins.apply("pack.publish-conventions")
    group = "org.geysermc.pack"
    version = "3.1-SNAPSHOT"

    java.sourceCompatibility = JavaVersion.VERSION_17
    java.targetCompatibility = JavaVersion.VERSION_17

    publishing {
        publications {
            register("publish", MavenPublication::class) {
                from(project.components["java"])
            }
        }
    }
}