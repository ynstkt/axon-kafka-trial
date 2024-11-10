plugins {
    val kotlinVersion = "2.0.0"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.5"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.example:axon_kafka_trial_shared")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.axonframework:axon-spring-boot-starter:4.10.2")
}

kotlin {
    jvmToolchain(21)
}