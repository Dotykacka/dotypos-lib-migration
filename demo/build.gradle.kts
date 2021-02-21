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

    // Random names generator
    implementation("io.github.serpro69:kotlin-faker:1.6.0")

    implementation("org.valiktor:valiktor-core:0.12.0")
}