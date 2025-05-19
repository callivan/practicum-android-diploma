package ru.practicum.android.diploma.data.typeAdapters

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.util.asSafeJsonObject
import ru.practicum.android.diploma.util.safeGetInt
import ru.practicum.android.diploma.util.safeGetJsonArray
import ru.practicum.android.diploma.util.safeGetJsonObject
import ru.practicum.android.diploma.util.safeGetString
import java.lang.reflect.Type

class VacancyResponseDeserializer : JsonDeserializer<VacancyDetails> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): VacancyDetails {
        val jsonObject = json.asJsonObject

        val id = jsonObject.get("id").asString
        val name = jsonObject.get("name").asString
        val description = jsonObject.get("description").asString
        val employer = jsonObject.safeGetJsonObject("employer")?.safeGetString("name")
        val logo = jsonObject.safeGetJsonObject("employer")?.safeGetJsonObject("logo_urls")?.safeGetString("90")
        val salaryRangeFrom = jsonObject.safeGetJsonObject("salary_range")?.safeGetInt("from")
        val salaryRangeTo = jsonObject.safeGetJsonObject("salary_range")?.safeGetInt("to")
        val salaryRangeCurrency = jsonObject.safeGetJsonObject("salary_range")?.safeGetString("currency")
        val city = jsonObject.safeGetJsonObject("address")?.safeGetString("city")
        val experience = jsonObject.safeGetJsonObject("experience")?.safeGetString("name")
        val keySkills =
            jsonObject.safeGetJsonArray("key_skills")?.mapNotNull { it.asSafeJsonObject()?.safeGetString("name") }
        val workFormat =
            jsonObject.safeGetJsonArray("work_format")?.mapNotNull { it.asSafeJsonObject()?.safeGetString("name") }

        return VacancyDetails(
            id = id,
            name = name,
            description = description,
            employer = employer,
            logo = logo,
            salaryRangeFrom = salaryRangeFrom,
            salaryRangeTo = salaryRangeTo,
            salaryRangeCurrency = salaryRangeCurrency,
            city = city,
            experience = experience,
            keySkills = keySkills,
            workFormat = workFormat
        )
    }
}
