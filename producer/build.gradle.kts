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
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.axonframework:axon-spring-boot-starter:4.10.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.1")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly ("com.mysql:mysql-connector-j")
    implementation("org.axonframework.extensions.kafka:axon-kafka-spring-boot-starter:4.10.0")
}

kotlin {
    jvmToolchain(21)
}
