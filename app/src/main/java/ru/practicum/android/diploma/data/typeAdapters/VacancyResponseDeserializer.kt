package ru.practicum.android.diploma.data.typeAdapters

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
        json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?
    ): VacancyDetailsDto {
        val jsonObject = json.asJsonObject

        val employer = jsonObject.safeGetJsonObject("employer")
        val salaryRange = jsonObject.safeGetJsonObject("salary_range")

        return VacancyDetailsDto(
            id = jsonObject.get("id").asString,
            name = jsonObject.get("name").asString,
            description = jsonObject.get("description").asString,
            employer = employer?.safeGetString("name"),
            logo = employer?.safeGetJsonObject("logo_urls")?.safeGetString("90"),
            salaryRangeFrom = salaryRange?.safeGetInt("from"),
            salaryRangeTo = salaryRange?.safeGetInt("to"),
            salaryRangeCurrency = salaryRange?.safeGetString("currency"),
            city = jsonObject.safeGetJsonObject("address")?.safeGetString("city"),
            experience = jsonObject.safeGetJsonObject("experience")?.safeGetString("name"),
            keySkills = jsonObject.safeGetJsonArray("key_skills")
                ?.mapNotNull { it.asSafeJsonObject()?.safeGetString("name") },
            workFormat = jsonObject.safeGetJsonArray("work_format")
                ?.mapNotNull { it.asSafeJsonObject()?.safeGetString("name") })
    }
}
