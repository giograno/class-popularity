/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Kotlin library project to get you started.
 */
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.3.41"
    id("com.github.johnrengelman.shadow") version "5.1.0"
    // Apply the java-library plugin for API and implementation separation.
    `java-library`
}

repositories {
    // Use jcenter for resolving dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
    mavenCentral()
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    implementation("com.fasterxml.jackson.core", name = "jackson-databind", version = "2.9.8")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.+")
    implementation("com.github.kittinunf.fuel", name = "fuel", version = "2.2.1")
    implementation("org.apache.opennlp", name = "opennlp-tools", version = "1.9.2")
    implementation("org.apache.commons", name = "commons-lang3", version = "3.9")
    implementation("org.jetbrains.kotlin", name = "kotlin-test-junit5", version = "1.3.61")
    implementation("io.github.microutils", name = "kotlin-logging", version="1.7.8")
    implementation("org.slf4j", name="slf4j-simple", version="1.7.26")

    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Use the Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

tasks.withType<ShadowJar> {
    manifest {
        attributes["Main-Class"] = "RunKt"
    }
}