group = "io.github.javaasasecondlanguage"
version = "1.0-SNAPSHOT"

plugins {
    java
    id("org.siouan.frontend") version "1.2.1"
}


java {
    sourceCompatibility = JavaVersion.VERSION_12
    targetCompatibility = JavaVersion.VERSION_12
}

repositories {
    mavenCentral()
}

frontend {
    nodeVersion.set("10.16.0")
    //cleanScript.set("run clean")
    installScript.set("install")
    assembleScript.set("run build")
}

tasks.named("jar", Jar::class) {
    dependsOn("assembleFrontend")
    from("$buildDir/dist")
    into("static")
}
