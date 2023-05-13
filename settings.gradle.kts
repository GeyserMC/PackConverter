rootProject.name = "packconverter-parent"

include(":bootstrap")
include(":converter")

include(":pack-schema-api")
include(":bedrock-pack-schema")
include(":schema-generator")

project(":pack-schema-api").projectDir = file("pack-schema/api")
project(":bedrock-pack-schema").projectDir = file("pack-schema/bedrock")
project(":schema-generator").projectDir = file("pack-schema/generator")