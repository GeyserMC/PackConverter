plugins {
    id("pack.base-conventions")
}

subprojects{
    plugins.apply("pack.base-conventions")
    plugins.apply("pack.publish-conventions")
    group = "org.geysermc.pack"
    version = "3.0-SNAPSHOT"

    java.sourceCompatibility = JavaVersion.VERSION_17
    java.targetCompatibility = JavaVersion.VERSION_17
}