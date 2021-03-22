package org.sharethemeal.translations.translations

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Test

class AndroidTranslatorTest {
    val jsonString = "{\n" +
            "    \"BADGE_YEAR\": {\n" +
            "        \"one\": \"year\",\n" +
            "        \"other\": \"years\"\n" +
            "    },\n" +
            "    \"INVITED_FRIEND\": {\n" +
            "        \"one\": \"friend invited\",\n" +
            "        \"other\": \"friends invited\"\n" +
            "    }" +
            "}"

    @Test
    fun testExample() {
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
        val translations = json.decodeFromString<Translation>(jsonString)
        assert(translations.values.size == 4)
    }
}
