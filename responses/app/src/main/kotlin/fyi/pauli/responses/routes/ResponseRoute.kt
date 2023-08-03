package fyi.pauli.responses.routes

import io.ktor.resources.*

@Resource("/{statusCode}")
class ResponseRoute(val statusCode: Int, val data: String? = "default")