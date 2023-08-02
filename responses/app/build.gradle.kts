import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
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
		kotlinOptions.jvmTarget = JvmTarget.JVM_19.target
	}

	compileJava {
		options.encoding = "UTF-8"
	}

	shadowJar {
		mergeServiceFiles()
		manifest {
			attributes(mapOf("Main-Class" to "fyi.pauli.responses.Responses"))
		}
	}

	build {
		dependsOn(shadowJar)
	}
}