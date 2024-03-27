plugins {
    kotlin("jvm") version "1.9.21"
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "com.sidapps.tictactoeai"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val javafxVersion = "22"
val javafxModules = listOf("javafx.controls", "javafx.graphics")

dependencies {
    testImplementation(kotlin("test"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.23")
    implementation("no.tornado:tornadofx:1.7.20")
    implementation("org.openjfx:javafx-controls:$javafxVersion")
    implementation("org.openjfx:javafx-graphics:$javafxVersion")
}

tasks.compileKotlin {
    kotlinOptions.jvmTarget = "17"
}

tasks.compileTestKotlin {
    kotlinOptions.jvmTarget = "17"
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}