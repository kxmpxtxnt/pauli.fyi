import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	application
	alias(libraries.plugins.jvm)
	alias(libraries.plugins.ktor)
	alias(libraries.plugins.serialization)
	alias(libraries.plugins.shadow)
}

group = "fyi.pauli"
version = "1.0"

repositories {
	mavenCentral()
}

dependencies {
	implementation(libraries.bundles.kotlinx)
	implementation(libraries.bundles.ktor)
	implementation(libraries.bundles.logging)
}

tasks {
	compileKotlin {
		kotlinOptions.jvmTarget = JvmTarget.JVM_17.target
	}

	compileJava {
		options.encoding = "UTF-8"
	}

	application {
		mainClass.set("fyi.pauli.responses.Responses")
	}

	build {
		dependsOn(application)
		dependsOn(shadowJar)
	}
}