group = "app.morphe"

patches {
    about {
        name = "RVX Morphed Evergreen"
        description = "Ungated RVX Morphed patches maintained by TheTahsinShahriar"
        source = "git@github.com:TheTahsinShahriar/RVX_Morphed_Evergreen.git"
        author = "TheTahsinShahriar, wchill & inotia00"
        contact = "https://github.com/TheTahsinShahriar"
        website = "https://github.com/TheTahsinShahriar/RVX_Morphed_Evergreen"
        license = "GNU General Public License v3.0"
    }
}

dependencies {
    // Used by JsonGenerator.
    implementation(libs.gson)
}

tasks {
    jar {
        exclude("app/morphe/generator")
    }
    register<JavaExec>("generatePatchesList") {
        description = "Build patch with patch list"

        dependsOn(build)

        classpath = sourceSets["main"].runtimeClasspath
        mainClass.set("app.morphe.generator.MainKt")
    }
    // Used by gradle-semantic-release-plugin.
    publish {
        dependsOn("generatePatchesList")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs = listOf("-Xcontext-receivers")
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/TheTahsinShahriar/RVX_Morphed_Evergreen")
            credentials {
                username = providers.gradleProperty("gpr.user").getOrElse(System.getenv("GITHUB_ACTOR"))
                password = providers.gradleProperty("gpr.key").getOrElse(System.getenv("GITHUB_TOKEN"))
            }
        }
    }
}
