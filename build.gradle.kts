plugins {
    kotlin("jvm") version "2.1.20"
}

group = "me.snipz"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.purpurmc.org/snapshots")

}

dependencies {
    compileOnly("org.purpurmc.purpur", "purpur-api", "1.21-R0.1-SNAPSHOT")
}

kotlin {
    jvmToolchain(21)
}