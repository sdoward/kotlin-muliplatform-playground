package org.sharethemeal.translations.translations

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import org.sharethemeal.translations.TranslationsDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(TranslationsDatabase.Schema, "test.db")
    }
}
