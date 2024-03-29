plugins {
    application
    kotlin("jvm") version "1.9.21"
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "com.sidapps.tictactoeai"
version = "1.0"

repositories {
    mavenCentral()
}

val javafxVersion = "22"

dependencies {
    testImplementation(kotlin("test"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.23")
    implementation("no.tornado:tornadofx:1.7.20")
    implementation("org.openjfx:javafx-fxml:$javafxVersion")
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

application {
    mainClass.set("com.sidapps.tictactoeai.MainKt")
}

tasks {
    val fatJar = register<Jar>("fatJar") {
        dependsOn.addAll(
            listOf(
                "compileJava",
                "compileKotlin",
                "processResources"
            )
        )
        archiveClassifier.set("standalone")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        manifest { attributes(mapOf("Main-Class" to application.mainClass)) }
        val sourcesMain = sourceSets.main.get()
        val contents = configurations.runtimeClasspath.get()
            .map { if (it.isDirectory) it else zipTree(it) } +
                sourcesMain.output
        from(contents)

        doLast {
            val jarFile = archiveFile.get().asFile
            val renamedFile = File(jarFile.parent, "TicTacToe-AI.jar")
            jarFile.renameTo(renamedFile)
            renamedFile.setExecutable(true)
        }
    }

    build {
        dependsOn(fatJar)
    }
}
