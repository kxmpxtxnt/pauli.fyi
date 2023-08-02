plugins {
	alias(libraries.plugins.jvm)
	alias(libraries.plugins.ktor)
	alias(libraries.plugins.serialization)
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