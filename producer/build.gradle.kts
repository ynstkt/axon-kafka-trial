plugins {
    val kotlinVersion = "2.0.0"
    kotlin("jvm") version kotlinVersion
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
}

kotlin {
    jvmToolchain(21)
}
