@file:Suppress("UnstableApiUsage")

rootProject.name = "packconverter-parent"

include(":bootstrap")
include(":converter")

include(":pack-schema-api")
include(":bedrock-pack-schema")
include(":schema-generator")

project(":pack-schema-api").projectDir = file("pack-schema/api")
project(":bedrock-pack-schema").projectDir = file("pack-schema/bedrock")
project(":schema-generator").projectDir = file("pack-schema/generator")

pluginManagement {
    includeBuild("build-logic")
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()

        // Geyser, Floodgate, Cumulus etc.
        maven("https://repo.opencollab.dev/main")
        maven("https://repo.opencollab.dev/maven-snapshots")

        // Java pack library
        // maven("https://repo.unnamed.team/repository/unnamed-public/")

        // This repo is dead.
//        maven { // We need a snapshot version of creative for now
//            url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
//        }

        // But we still need creative... maven local it is
        mavenLocal()
    }
}