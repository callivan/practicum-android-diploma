package ru.practicum.android.diploma.data.deserializers

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import ru.practicum.android.diploma.data.dto.VacancyDetailsDto
import ru.practicum.android.diploma.util.asSafeJsonObject
import ru.practicum.android.diploma.util.safeGetInt
import ru.practicum.android.diploma.util.safeGetJsonArray
import ru.practicum.android.diploma.util.safeGetJsonObject
import ru.practicum.android.diploma.util.safeGetString
import java.lang.reflect.Type

class VacancyResponseDeserializer : JsonDeserializer<VacancyDetailsDto> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): VacancyDetailsDto {
        val jsonObject = json.asJsonObject

        val employer = jsonObject.safeGetJsonObject("employer")
        val salaryRange = jsonObject.safeGetJsonObject("salary_range")

        val areaName = jsonObject.safeGetJsonObject("area")?.safeGetString("name")
        val address = jsonObject.safeGetJsonObject("address")
        val city = address?.safeGetString("city")
        val street = address?.safeGetString("street")
        val building = address?.safeGetString("building")
        val fullAddress = "$city, $street, $building"

        return VacancyDetailsDto(
            id = jsonObject.get("id").asString,
            name = jsonObject.get("name").asString,
            description = jsonObject.get("description").asString,
            alternateUrl = jsonObject.get("alternate_url").asString,
            employer = employer?.safeGetString("name"),
            logo = employer?.safeGetJsonObject("logo_urls")?.safeGetString("90"),
            salaryRangeFrom = salaryRange?.safeGetInt("from"),
            salaryRangeTo = salaryRange?.safeGetInt("to"),
            salaryRangeCurrency = salaryRange?.safeGetString("currency"),
            address = if (city != null && street != null && building != null) fullAddress else areaName,
            experience = jsonObject.safeGetJsonObject("experience")?.safeGetString("name"),
            keySkills = jsonObject.safeGetJsonArray("key_skills")
                ?.mapNotNull { it.asSafeJsonObject()?.safeGetString("name") },
            workFormat = jsonObject.safeGetJsonArray("work_format")
                ?.mapNotNull { it.asSafeJsonObject()?.safeGetString("name") })
    }
}
