plugins {
    kotlin("jvm") version "1.4.21"
    id("org.jetbrains.dokka") version "1.4.20"
}

val kotlinVersion: String by extra("1.4.21")
val projectGroup: String by extra("com.dotypos.lib.migration")
val projectVersion: String by extra("0.4.7")

allprojects {
    repositories {
        mavenCentral()
        jcenter()
    }
}