plugins {
    kotlin("jvm") version "1.9.21"
}

group = "bangma"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // dev
    implementation("io.github.libsdl4j:libsdl4j:2.28.4-1.6")

    // test
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}