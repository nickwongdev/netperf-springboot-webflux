import org.gradle.api.tasks.bundling.Jar

/* Apply the plugins to the build */
plugins {
	kotlin("jvm") version "1.3.11"
	id("org.jetbrains.kotlin.plugin.spring") version "1.3.11"
	id("org.springframework.boot") version "2.1.1.RELEASE"
	`maven-publish`
}

group = "com.nickwongdev.netperf"
version = "1.0-SNAPSHOT"

repositories {
	mavenLocal()
	mavenCentral()
}

dependencies {
	compile(kotlin("stdlib-jdk8"))
	compile(kotlin("reflect"))
	compile("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.1.0")
	compile("com.nickwongdev.netperf:benchmark-service:1.0-SNAPSHOT")
	compile("org.springframework.boot:spring-boot-starter-webflux:2.1.1.RELEASE")
	compile("com.fasterxml.jackson.module:jackson-module-kotlin")

	testCompile("org.jetbrains.kotlinx:kotlinx-coroutines-debug:1.1.0")
	testCompile("org.springframework.boot:spring-boot-starter-test")
	testCompile("io.projectreactor:reactor-test")
}
