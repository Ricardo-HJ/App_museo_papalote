// build.gradle.kts (Project)
plugins {
    id("com.android.application") version "8.7.2" apply false
    id("org.jetbrains.kotlin.android") version "2.0.0" apply false
}

buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.2")
        classpath("com.android.tools.build:gradle:8.7.2")

    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}