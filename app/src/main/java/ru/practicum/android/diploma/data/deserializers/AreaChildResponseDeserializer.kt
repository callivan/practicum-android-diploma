package ru.practicum.android.diploma.data.deserializers

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import ru.practicum.android.diploma.data.dto.AreaChildResponseDto
import ru.practicum.android.diploma.data.dto.AreaDto
import java.lang.reflect.Type

class AreaChildResponseDeserializer : JsonDeserializer<AreaChildResponseDto> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): AreaChildResponseDto {
        val jsonObject = json.asJsonObject

        val areas = jsonObject.getAsJsonArray("areas").mapNotNull { area ->
            val areaAsJson = area.asJsonObject

            AreaDto(
                id = areaAsJson.get("id").asString,
                name = areaAsJson.get("name").asString
            )
        }

        return AreaChildResponseDto(areas = areas)
    }
}
