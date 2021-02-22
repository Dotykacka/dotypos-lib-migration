plugins {
    kotlin("jvm") version "1.4.21"
    id("org.jetbrains.dokka") version "1.4.20"
}

val kotlinVersion: String by extra("1.4.21")

allprojects {
    repositories {
        mavenCentral()
        jcenter()
    }
}