package translations

import kotlin.String

data class Translations(
  val key: String,
  val value: String
) {
  override fun toString(): String = """
  |Translations [
  |  key: $key
  |  value: $value
  |]
  """.trimMargin()
}
