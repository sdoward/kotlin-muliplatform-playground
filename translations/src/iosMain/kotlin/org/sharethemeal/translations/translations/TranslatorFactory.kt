package org.sharethemeal.translations.translations

import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import org.sharethemeal.translations.TranslationsDatabase

object TranslatorFactory {

    private var translator: Translator? = null

    fun init() {
        val sqlDriver = NativeSqliteDriver(
            schema = TranslationsDatabase.Schema,
            name = "test.db"
        )
        val database = TranslationsDatabase(sqlDriver)
        translator = Translator(TranslationsApi(), database)
    }

    fun get() = translator ?: throw IllegalStateException("Init should be called before get()")

}
