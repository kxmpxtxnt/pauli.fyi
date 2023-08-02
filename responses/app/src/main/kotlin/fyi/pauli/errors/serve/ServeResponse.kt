package fyi.pauli.errors.serve

import fyi.pauli.errors.configs.ResponseFilesConfig
import fyi.pauli.errors.responseFilesConfig
import fyi.pauli.errors.routes.ResponseRoute
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*

fun Routing.serverResponse() = get<ResponseRoute> { route ->
	val code: Int = route.statusCode.toIntOrNull() ?: Int.MIN_VALUE

	if (code == Int.MIN_VALUE) {
		call.respondRedirect {
			path("/404")
		}
	}

	val responseFile = responseFilesConfig().codes.find {
		it.code == code && it.exists
	}
		?: ResponseFilesConfig.ResponseFile.responseNotFound(code)

	when (route.data) {
		"raw" -> call.respond(responseFile.raw())
		else -> {
			val stream = this::class.java.getResourceAsStream(responseFile.path)

			checkNotNull(stream) { "0.gif does not exist. This gif should be the default for any state of the app." }

			call.respondHtml {
				head {
					style {
						+"""
                body {
                    background-color: black;
                    color: white;
                    font-family: roxima-nova, Helvetica Neue, Helvetica, Arial, sans-serif;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    height: 100vh;
                    margin: 0;
                }
                """
					}
				}
				body {
					div {
						style = "text-align: center;"
						img("$code - ${responseFile.description}", responseFile.path)
						br { }
						h1 {
							+"$code"
						}
						h3 {
							+responseFile.description
						}
					}
				}
			}
		}
	}
}