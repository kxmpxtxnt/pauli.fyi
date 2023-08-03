package fyi.pauli.responses

import fyi.pauli.responses.configs.ResponseFilesConfig
import fyi.pauli.responses.serve.serverResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory
import org.slf4j.event.Level

val SERVER_PORT: Int = /*System.getenv("SERVER_PORT").toInt()*/ 3000
val SERVER_HOST: String = /*System.getenv("SERVER_HOST") */ "0.0.0.0"
val DEBUG: Boolean = /*System.getenv("DEBUG").toBoolean() */ true

val responseFilesConfig: () -> ResponseFilesConfig
	get() = {
		val bytes = ResponseFilesConfig::class.java.getResourceAsStream("/responses.json")?.readBytes()

		checkNotNull(bytes) {
			"Configuration file responses.json should not be empty or existing."
		}

		json().decodeFromString<ResponseFilesConfig>(String(bytes))
	}

fun main() {
	embeddedServer(
		CIO,
		port = SERVER_PORT,
		host = SERVER_HOST,
		module = Application::responseServerModule
	).start(wait = true)
}

fun json() = Json {
	this.prettyPrint = DEBUG
}

private fun Application.responseServerModule() {
	responseFilesConfig()
	install(ContentNegotiation) {
		json()
	}
	install(Resources)
	install(Routing)
	install(CallLogging) {
		this.logger = LoggerFactory.getLogger("fyi.pauli.responses.Responses")
		this.level = if (DEBUG) Level.DEBUG else Level.INFO
	}

	routing {
		staticResources("/responses", "responses")

		val faviconBytes by lazy {
			this::class.java.getResourceAsStream("/favicon.ico")?.readBytes() ?: byteArrayOf()
		}

		serverResponse()

		get("/favicon.ico") {
			call.respondBytes(
				faviconBytes
			)
		}

		get("{...}") {
			call.respondRedirect {
				path("/404")
			}
		}
	}
}