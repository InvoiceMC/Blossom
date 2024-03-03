plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("co.uzzu.dotenv.gradle") version "4.0.0"
    kotlin("jvm")
    `maven-publish`
    java
}

group = "me.outspending"
version = "0.0.1"

repositories {
    mavenCentral()
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
    maven { url = uri("https://oss.sonatype.org/content/groups/public/") }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    implementation(kotlin("stdlib-jdk8"))
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

tasks {
    wrapper {
        gradleVersion = "8.6"
        distributionType = Wrapper.DistributionType.ALL
    }

    shadowJar {
        if (!env.isPresent("DESTINATION")) {
            throw IllegalArgumentException("DESTINATION environment variable is not set")
        }

        val destination = env.DESTINATION.value
        destinationDirectory.set(file(destination))
    }
}

kotlin {
    jvmToolchain(17)
}