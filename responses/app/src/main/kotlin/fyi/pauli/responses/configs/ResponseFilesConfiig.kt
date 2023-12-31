package fyi.pauli.responses.configs

import fyi.pauli.responses.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString

@Serializable
data class ResponseFilesConfig(
	val codes: ArrayList<ResponseFile> = arrayListOf()
) {

	@Serializable
	data class ResponseFile(
		val code: Int,
		val description: String,
		private val fileName: String,
		val exists: Boolean
	) {

		fun raw() = json().encodeToString(this)

		companion object {
			fun responseNotFound(code: Int) = ResponseFile(
				0,
				"Response '$code' does not exists at the moment. Sorry :(",
				"0.gif",
				true
			)
		}

		val path: String
			get() = "/responses/$fileName"
	}
}