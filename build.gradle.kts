plugins {
    id("pack.base-conventions")
}

subprojects {
    plugins.apply("pack.base-conventions")
    if (name != "schema-generator") {
        plugins.apply("pack.publish-conventions")
    }

    java.sourceCompatibility = JavaVersion.VERSION_21
    java.targetCompatibility = JavaVersion.VERSION_21
}