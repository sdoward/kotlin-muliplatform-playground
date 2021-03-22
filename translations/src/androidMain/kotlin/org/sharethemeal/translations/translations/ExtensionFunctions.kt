package org.sharethemeal.translations.translations

import android.widget.TextView

fun TextView.setTranslation(key: String) {
    val translation = TranslatorFactory.get().getTranslation(key)
    text = translation
}
