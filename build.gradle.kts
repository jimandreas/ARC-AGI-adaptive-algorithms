/*
plugins {
    kotlin("jvm") version "2.0.20"
}

group = "com.jimandreas"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}*/

plugins {
    kotlin("jvm") version "1.8.22" // Use the latest Kotlin version
    kotlin("plugin.serialization") version "2.0.21"
}

group = "com.jimandreas"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    // SQLite JDBC driver
    implementation("org.xerial:sqlite-jdbc:3.41.2.2")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
    testImplementation(kotlin("test"))

    testImplementation("org.jetbrains.kotlin:kotlin-test:1.6.0")
    testImplementation(platform("org.junit:junit-bom:5.8.1"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")

}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(22) // Use Java 17 or higher
}

