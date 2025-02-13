package com.example.plantapp

import com.google.gson.*
import java.lang.reflect.Type

class SynonymsDeserializer : JsonDeserializer<List<String>> {
    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): List<String> {
        val synonyms = mutableListOf<String>()
        if (json.isJsonArray) {
            json.asJsonArray.forEach { element ->
                if (element.isJsonObject) {
                    synonyms.add(element.asJsonObject.get("name").asString)
                } else if (element.isJsonPrimitive) {
                    synonyms.add(element.asString)
                }
            }
        }
        return synonyms
    }
}
