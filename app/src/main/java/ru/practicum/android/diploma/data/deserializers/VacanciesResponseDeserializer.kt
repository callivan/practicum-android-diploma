package ru.practicum.android.diploma.data.deserializers

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import ru.practicum.android.diploma.data.dto.VacanciesResponseDto
import ru.practicum.android.diploma.data.dto.VacancyShortDto
import ru.practicum.android.diploma.util.safeGetInt
import ru.practicum.android.diploma.util.safeGetJsonObject
import ru.practicum.android.diploma.util.safeGetString
import java.lang.reflect.Type

class VacanciesResponseDeserializer : JsonDeserializer<VacanciesResponseDto> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): VacanciesResponseDto {
        val jsonObject = json.asJsonObject

        val items = jsonObject.getAsJsonArray("items").mapNotNull { vacancy ->
            val vacancyAsJson = vacancy.asJsonObject

            val employer = vacancyAsJson.safeGetJsonObject("employer")
            val salaryRange = vacancyAsJson.safeGetJsonObject("salary_range")

            val areaName = jsonObject.safeGetJsonObject("area")?.safeGetString("name")
            val address = jsonObject.safeGetJsonObject("address")
            val city = address?.safeGetString("city")
            val street = address?.safeGetString("street")
            val building = address?.safeGetString("building")
            val fullAddress = "$city, $street, $building"

            VacancyShortDto(
                id = vacancyAsJson.get("id").asString,
                name = vacancyAsJson.get("name").asString,
                employer = employer?.safeGetString("name"),
                logo = employer?.safeGetJsonObject("logo_urls")?.safeGetString("90"),
                salaryRangeFrom = salaryRange?.safeGetInt("from"),
                salaryRangeTo = salaryRange?.safeGetInt("to"),
                salaryRangeCurrency = salaryRange?.safeGetString("currency"),
                address = if (city != null && street != null && building != null) fullAddress else areaName,
            )
        }
        val found = jsonObject.get("found").asInt
        val page = jsonObject.get("page").asInt
        val pages = jsonObject.get("pages").asInt

        return VacanciesResponseDto(
            items = items,
            found = found,
            page = page,
            pages = pages
        )
    }
}
