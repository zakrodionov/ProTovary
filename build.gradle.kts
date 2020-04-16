import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
        maven("https://maven.fabric.io/public")
    }
    dependencies {
        classpath(Libs.android_gradle_plugin)
        classpath(Libs.kotlin_gradle_plugin)
        classpath(Libs.google_services_plugin)
        classpath(Libs.fabric_plugin)
        classpath(Libs.navigation_safe_args_plugin)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven("https://maven.google.com/")
        maven("https://jitpack.io")
    }
}

plugins {
    id(Libs.gradle_versions_plugin) version Versions.gradle_versions_plugin
    id(Libs.ktlint_plugin) version Versions.ktlint_plugin_version
}

task("clean") {
    delete(rootProject.buildDir)
}

tasks.withType<DependencyUpdatesTask> {
        resolutionStrategy {
            componentSelection {
                all {
                    val rejected = listOf<String>(/*"alpha", "beta", "rc", "cr", "m", "preview"*/)
                        .any { qualifier ->
                            candidate.version.matches(Regex("(?i).*[.-]$qualifier[.\\d-+]*"))
                        }

                    if (rejected) {
                        reject("Release candidate")
                    }
                }
            }
        }

        outputFormatter = "plain"
        outputDir = "build/dependencyUpdates"
        reportfileName = "report"
    }
