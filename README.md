# PackConverter

[![License: MIT](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Discord](https://img.shields.io/discord/613163671870242838.svg?color=%237289da&label=discord)](http://discord.geysermc.org/)

PackConverter is a library for converting Java Edition resource packs to Bedrock Edition.

This is based on the Node.js module ConvertJavaTextureToBedrockApi by ozelot379.

**Please note, this project is still a work in progress and should not be used on production. Expect bugs!**

**This project also does not convert custom items fully, it will only convert the textures, but does not create any Geyser mappings.**

If you are looking for a program capable of creating such custom item mappings, take a look at [Rainbow](https://github.com/GeyserMC/Rainbow/).

## Usage
- Ensure Java is installed, you can use [PaperMC's guide](https://docs.papermc.io/misc/java-install/) on installing Java if you do not have Java installed.
- Download Thunder, the PackConverter GUI, from the Actions tab on GitHub.
- Double-click on the JAR file to open up the UI, then select your Java pack and hit convert!

## CLI Usage
You can also use PackConverter in a CLI, by downloading Thunder (see `Usage`) then running the JAR file with some parameters, an example can be seen below:

```bash
java -jar Thunder.jar nogui --input "C:\path\to\pack.zip"
```

You can also enable debug mode by adding `debug` as an additional argument, this also works for the GUI.

## Automated validation

The fork includes an unattended validation pipeline intended to keep regressions visible without requiring a developer to be online.

- The normal build runs on pushes, manual dispatches, and a six-hour schedule.
- The scheduled deep-validation workflow runs daily and performs repository integrity checks, resource-tree validation, converter regression tests, `check`, and a clean full build.
- Converter tests cover mod-JAR resource extraction, deterministic collision handling, metadata isolation, and Zip-Slip protection.
- CI retries Gradle operations for transient infrastructure failures while preserving a real failure when the underlying build or tests remain broken.
- Fork builds never enter the upstream Maven/download publishing path.
- Failed unattended runs upload build/test diagnostics as artifacts for later inspection.

## Compiling
1. Clone the repo to your computer
2. Run `gradlew build` and locate the output in the `bootstrap/build` folder.
