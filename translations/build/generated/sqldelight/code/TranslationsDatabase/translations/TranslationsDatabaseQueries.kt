package translations

import com.squareup.sqldelight.Query
import com.squareup.sqldelight.Transacter
import kotlin.Long
import kotlin.String

interface TranslationsDatabaseQueries : Transacter {
  fun getTranslation(key: String): Query<String>

  fun translationsCount(): Query<Long>

  fun insertTranslation(key: String, value: String)

  fun deleteAll()
}
