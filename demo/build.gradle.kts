val kotlinVersion: String by rootProject.extra

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    implementation(project(":lib"))
    implementation(kotlin("script-runtime"))
}