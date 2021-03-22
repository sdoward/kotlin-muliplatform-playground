package org.sharethemeal.translations.translations

import com.squareup.sqldelight.Query
import com.squareup.sqldelight.TransacterImpl
import com.squareup.sqldelight.db.SqlCursor
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.internal.copyOnWriteList
import kotlin.Any
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.collections.MutableList
import kotlin.jvm.JvmField
import kotlin.reflect.KClass
import org.sharethemeal.translations.TranslationsDatabase
import translations.TranslationsDatabaseQueries

internal val KClass<TranslationsDatabase>.schema: SqlDriver.Schema
  get() = TranslationsDatabaseImpl.Schema

internal fun KClass<TranslationsDatabase>.newInstance(driver: SqlDriver): TranslationsDatabase =
    TranslationsDatabaseImpl(driver)

private class TranslationsDatabaseImpl(
  driver: SqlDriver
) : TransacterImpl(driver), TranslationsDatabase {
  override val translationsDatabaseQueries: TranslationsDatabaseQueriesImpl =
      TranslationsDatabaseQueriesImpl(this, driver)

  object Schema : SqlDriver.Schema {
    override val version: Int
      get() = 1

    override fun create(driver: SqlDriver) {
      driver.execute(null, """
          |CREATE TABLE Translations (
          |    key TEXT NOT NULL UNIQUE PRIMARY KEY,
          |    value TEXT NOT NULL
          |)
          """.trimMargin(), 0)
    }

    override fun migrate(
      driver: SqlDriver,
      oldVersion: Int,
      newVersion: Int
    ) {
    }
  }
}

private class TranslationsDatabaseQueriesImpl(
  private val database: TranslationsDatabaseImpl,
  private val driver: SqlDriver
) : TransacterImpl(driver), TranslationsDatabaseQueries {
  internal val getTranslation: MutableList<Query<*>> = copyOnWriteList()

  internal val translationsCount: MutableList<Query<*>> = copyOnWriteList()

  override fun getTranslation(key: String): Query<String> = GetTranslationQuery(key) { cursor ->
    cursor.getString(0)!!
  }

  override fun translationsCount(): Query<Long> = Query(-2000838582, translationsCount, driver,
      "TranslationsDatabase.sq", "translationsCount", """
  |SELECT count(*)
  |FROM Translations
  """.trimMargin()) { cursor ->
    cursor.getLong(0)!!
  }

  override fun insertTranslation(key: String, value: String) {
    driver.execute(-1227448267, """
    |INSERT INTO Translations(key, value)
    |VALUES(?,?)
    """.trimMargin(), 2) {
      bindString(1, key)
      bindString(2, value)
    }
    notifyQueries(-1227448267, {database.translationsDatabaseQueries.getTranslation +
        database.translationsDatabaseQueries.translationsCount})
  }

  override fun deleteAll() {
    driver.execute(30337779, """DELETE FROM Translations""", 0)
    notifyQueries(30337779, {database.translationsDatabaseQueries.getTranslation +
        database.translationsDatabaseQueries.translationsCount})
  }

  private inner class GetTranslationQuery<out T : Any>(
    @JvmField
    val key: String,
    mapper: (SqlCursor) -> T
  ) : Query<T>(getTranslation, mapper) {
    override fun execute(): SqlCursor = driver.executeQuery(1549581470, """
    |SELECT value
    |FROM Translations
    |WHERE key = ?
    """.trimMargin(), 1) {
      bindString(1, key)
    }

    override fun toString(): String = "TranslationsDatabase.sq:getTranslation"
  }
}
