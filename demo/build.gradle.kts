val kotlinVersion: String by rootProject.extra
val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks

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

    implementation("org.valiktor:valiktor-core:0.12.0")
}

compileKotlin.kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()