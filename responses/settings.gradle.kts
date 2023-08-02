rootProject.name = "responses.pauli.fyi"

include(
	"app"
)

dependencyResolutionManagement {
	versionCatalogs {
		create("libraries") {
			version("kotlin", "1.9.0")
			version("ktor", "2.3.2")

			plugin("jvm", "org.jetbrains.kotlin.jvm").versionRef("kotlin")
			plugin("serialization", "org.jetbrains.kotlin.plugin.serialization").versionRef("kotlin")
			plugin("ktor", "io.ktor.plugin").versionRef("ktor")
			plugin("shadow", "com.github.johnrengelman.shadow").version("8.1.1")

			version("coroutines", "1.7.3")
			version("serialization", "1.5.1")

			library(
				"kotlinx.serialization",
				"org.jetbrains.kotlinx",
				"kotlinx-serialization-json"
			).versionRef("serialization")
			library("kotlinx.coroutines", "org.jetbrains.kotlinx", "kotlinx-coroutines-core").versionRef("coroutines")

			bundle("kotlinx", listOf("kotlinx.serialization", "kotlinx.coroutines"))

			library("ktor.server.cio", "io.ktor", "ktor-server-cio").withoutVersion()
			library("ktor.server.core", "io.ktor", "ktor-server-core").withoutVersion()
			library("ktor.server.resources", "io.ktor", "ktor-server-resources").withoutVersion()
			library("ktor.server.negotiation", "io.ktor", "ktor-server-content-negotiation").withoutVersion()
			library("ktor.server.logging", "io.ktor", "ktor-server-call-logging").withoutVersion()
			library("ktor.server.html", "io.ktor", "ktor-server-html-builder").withoutVersion()

			bundle(
				"ktor",
				listOf(
					"ktor.server.core",
					"ktor.server.cio",
					"ktor.server.resources",
					"ktor.server.negotiation",
					"ktor.server.logging",
					"ktor.server.html"
				)
			)

			version("logback", "1.4.8")
			version("kotlin-logging", "5.0.1")

			library("logging.logback", "ch.qos.logback", "logback-classic").versionRef("logback")
			library("logging.kotlin", "io.github.oshai", "kotlin-logging-jvm").versionRef("kotlin-logging")

			bundle("logging", listOf("logging.logback", "logging.kotlin"))
		}
	}
}