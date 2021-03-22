package org.sharethemeal.translations.translations

import android.util.Log

actual object Logger {
    actual fun log(message: String) {
        Log.d("Translator", message)
    }
}
