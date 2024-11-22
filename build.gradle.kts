plugins {
    id("java")
    id("application")
}

application {
    mainClass.set("Main")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation ("com.formdev:flatlaf:3.5.2")
}

tasks.test {
    useJUnitPlatform()
}