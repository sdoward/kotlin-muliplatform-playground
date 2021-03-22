package org.sharethemeal.translations

import com.squareup.sqldelight.Transacter
import com.squareup.sqldelight.db.SqlDriver
import org.sharethemeal.translations.translations.newInstance
import org.sharethemeal.translations.translations.schema
import translations.TranslationsDatabaseQueries

interface TranslationsDatabase : Transacter {
  val translationsDatabaseQueries: TranslationsDatabaseQueries

  companion object {
    val Schema: SqlDriver.Schema
      get() = TranslationsDatabase::class.schema

    operator fun invoke(driver: SqlDriver): TranslationsDatabase =
        TranslationsDatabase::class.newInstance(driver)}
}
