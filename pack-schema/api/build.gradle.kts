dependencies {
    compileOnly(project(":bedrock-pack-schema")) // Available on compile, but not runtime classpath - we shade it in task below
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.jetbrains:annotations:24.0.1")
}

val bedrockPackSchemaSourceSet = project(":bedrock-pack-schema").sourceSets.getByName("main")

tasks.jar {
    from(bedrockPackSchemaSourceSet.output)
    duplicatesStrategy = DuplicatesStrategy.WARN
}
