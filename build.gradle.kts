plugins {
    id("pack.base-conventions")
}

subprojects {
    plugins.apply("pack.base-conventions")
    plugins.apply("pack.publish-conventions")

    java.sourceCompatibility = JavaVersion.VERSION_25
    java.targetCompatibility = JavaVersion.VERSION_25
}
