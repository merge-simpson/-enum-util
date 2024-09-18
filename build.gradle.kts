plugins {
    id("java")
}

group = "me.letsdev"
version = "0.1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

}

tasks.test {
    useJUnitPlatform()
}