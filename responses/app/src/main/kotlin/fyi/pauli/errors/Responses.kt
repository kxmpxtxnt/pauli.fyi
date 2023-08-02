package fyi.pauli.errors

import fyi.pauli.errors.configs.ResponseFilesConfig
import fyi.pauli.errors.serve.serverResponse
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
import kotlinx.serialization.json.decodeFromStream
import org.slf4j.LoggerFactory
import org.slf4j.event.Level

val SERVER_PORT: Int = System.getenv("SERVER_PORT").toInt()
val SERVER_HOST: String = System.getenv("SERVER_HOST")
val DEBUG: Boolean = System.getenv("DEBUG").toBoolean()

@OptIn(ExperimentalSerializationApi::class)
val responseFilesConfig: () -> ResponseFilesConfig
	get() = {
		val stream = ResponseFilesConfig::class.java.getResourceAsStream("/responses.json")

		if (stream != null) {
			json().decodeFromStream<ResponseFilesConfig>(stream)
		} else ResponseFilesConfig()
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