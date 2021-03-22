package org.sharethemeal.translations.translations

import org.sharethemeal.translations.TranslationsDatabase

class Translator(
    private val translationsApi: TranslationsApi,
    private val database: TranslationsDatabase
) {

    fun getTranslation(key: String): String {
        return database.translationsDatabaseQueries.getTranslation(key).executeAsOne()
    }

    suspend fun downloadTranslations(url: String) {
        try {
            Logger.log("Downloading translations")
            val translations = translationsApi.getTranslations(url)
            Logger.log("Downloaded translation")
            setTranslations(translations)
        } catch (exception: Exception) {
            Logger.log("Downloading translations failed")
            if (database.haveTranslations()) {
                Logger.log("Existing translations found. Continuing without exception")
                return
            } else {
                throw TranslationNotRetrievableException(exception)
            }
        }
    }

    private fun setTranslations(translations: Translation) {
        Logger.log("Committing translation")
        database.translationsDatabaseQueries.transaction {
            afterCommit { Logger.log("Committed translations") }
            afterRollback { Logger.log("Committing translation failed. Rolled back") }
            database.translationsDatabaseQueries.deleteAll()
            translations.values.forEach {
                database.translationsDatabaseQueries.insertTranslation(it.key, it.value)
            }
        }
    }
}

class TranslationNotRetrievableException(cause: Exception) : Exception(cause)

fun TranslationsDatabase.haveTranslations() =
    translationsDatabaseQueries.translationsCount().executeAsOne() > 1

