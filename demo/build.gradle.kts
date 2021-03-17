val kotlinVersion: String by rootProject.extra

plugins {
    kotlin("jvm")
    application
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    implementation(project(":lib"))
    implementation(kotlin("script-runtime"))

    // Random names generator
    implementation("io.github.serpro69:kotlin-faker:1.6.0")
}

application {
    mainClass.set("com.dotypos.lib.migration.demo.AppKt")
    applicationDefaultJvmArgs = listOf(
        "-Xms4G",
        "-Xmx32G",
        "-XX:-UseGCOverheadLimit",
        "-XX:+UseG1GC"
    )
}