package org.sharethemeal.translations.translations

import io.ktor.client.HttpClient
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*

class TranslationsApi {

    private val httpClient = HttpClient {
        install(JsonFeature) {
            val json = kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
                isLenient = true
            }
            serializer = KotlinxSerializer(json)
        }
    }

    suspend fun getTranslations(translationUrl: String): Translation {
        return httpClient.get(translationUrl)
    }

}

@Serializable(with = TranslationSerializer::class)
data class Translation(
    val values: Map<String, String>
)

object TranslationSerializer : KSerializer<Translation> {

    override val descriptor = buildClassSerialDescriptor("Translation") {}

    override fun deserialize(decoder: Decoder): Translation {
        val jsonDecoder = (decoder as JsonDecoder)
        val jsonObject = jsonDecoder.decodeJsonElement().jsonObject
        val translations = mutableMapOf<String, String>()
        getMap(jsonObject.entries, translations)
        return Translation(translations)
    }

    override fun serialize(encoder: Encoder, value: Translation) {
    }

    private fun getMap(
        entrySet: Set<Map.Entry<String, JsonElement>>,
        translationValues: MutableMap<String, String>,
        key: String = ""
    ) {
        entrySet.forEach {
            val amendedKey = if (key.isEmpty()) {
                it.key
            } else {
                key + ".${it.key}"
            }
            if (it.value is JsonPrimitive) {
                translationValues[amendedKey] = it.value.jsonPrimitive.content
            } else {
                getMap(it.value.jsonObject.entries, translationValues, amendedKey)
            }
        }
    }

}
