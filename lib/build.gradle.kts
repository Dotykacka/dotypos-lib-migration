import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion: String by rootProject.extra

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("org.jetbrains.dokka")
    id("java-library")
    id("maven-publish")
}

group = "com.dotypos.lib.migration"
version = "0.2.9"

dependencies {
    // Implementation
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")

    // Entity validation
    implementation("org.valiktor:valiktor-core:0.12.0")

    // Documentation
    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    runtimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")
    implementation(kotlin("script-runtime"))
}

java {
    withSourcesJar()
    withJavadocJar()
}

tasks.jar {
    manifest {
        attributes(
            mapOf(
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version
            )
        )
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}


publishing {
    publications {
        register<MavenPublication>("gpr") {
            groupId = "com.dotypos.lib.migration"
            artifactId = "lib-migration"
            from(components["java"])
            version = project.version.toString()

            pom {
                name.set("Dotypos Data Migration Library")
                developers {
                    developer {
                        id.set("tomas.sustek")
                        name.set("Tomáš Šůstek")
                        email.set("tomas.sustek@dotykacka.cz")
                    }
                }
            }
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/Dotykacka/dotypos-lib-migration")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GHP_USER")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("GHP_TOKEN")
            }
        }
    }
}