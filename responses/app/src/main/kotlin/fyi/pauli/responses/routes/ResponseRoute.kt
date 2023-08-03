package fyi.pauli.responses.routes

import io.ktor.resources.*

@Resource("/{statusCode}")
class ResponseRoute(val statusCode: String, val data: String? = "default")