package org.sharethemeal.translations.translations

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.sharethemeal.translations.TranslationsDatabase

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(TranslationsDatabase.Schema , context, "test.db")
    }
}
