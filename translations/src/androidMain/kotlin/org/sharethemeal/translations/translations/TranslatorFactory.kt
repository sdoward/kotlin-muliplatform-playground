package org.sharethemeal.translations.translations

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import org.sharethemeal.translations.TranslationsDatabase

object TranslatorFactory {

    private var translator: Translator? = null

    fun init(context: Context) {
        val sqlDriver = AndroidSqliteDriver(
            schema = TranslationsDatabase.Schema,
            context = context,
            name = "translations.db"
        )
        val database = TranslationsDatabase(sqlDriver)
        translator = Translator(TranslationsApi(), database)
    }

    fun get() = translator ?: throw IllegalStateException("Init should be called before get()")

}
