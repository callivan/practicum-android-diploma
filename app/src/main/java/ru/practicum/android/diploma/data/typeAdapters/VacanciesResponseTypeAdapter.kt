package ru.practicum.android.diploma.data.typeAdapters

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import ru.practicum.android.diploma.data.dto.VacanciesResponseDto
import ru.practicum.android.diploma.data.dto.VacancyShortDto

class VacanciesResponseTypeAdapter : TypeAdapter<VacanciesResponseDto>() {
    override fun write(
        out: JsonWriter?, value: VacanciesResponseDto?
    ) {
        TODO("Not yet implemented")
    }

    override fun read(reader: JsonReader): VacanciesResponseDto {
        var vacancies = emptyList<VacancyShortDto>()
        var page = 0
        var pages = 0
        var found = 0

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.nextName()) {
                "items" -> {
                    vacancies = parseVacancies(reader)
                }

                "fund" -> found = reader.nextInt()
                "page" -> page = reader.nextInt()
                "pages" -> pages = reader.nextInt()

                else -> reader.skipValue()
            }
        }
        reader.endObject()

        return VacanciesResponseDto(items = vacancies, page = page, pages = pages, found = found)
    }

    private fun parseVacancies(reader: JsonReader): List<VacancyShortDto> {
        val vacancies = mutableListOf<VacancyShortDto>()
        reader.beginArray()
        while (reader.hasNext()) {
            vacancies.add(parseVacancyShort(reader))
        }
        reader.endArray()
        return vacancies
    }

    private fun parseVacancyShort(reader: JsonReader): VacancyShortDto {
        var id = ""
        var name = ""
        var employer: String? = null
        var logo: String? = null
        var salaryRangeFrom: Int? = null
        var salaryRangeTo: Int? = null
        var salaryRangeCurrency: String? = null
        var city: String? = null

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.nextName()) {
                "id" -> id = reader.nextString()
                "name" -> name = reader.nextString()
                "employer" -> {

                    if (reader.peek() == JsonToken.NULL) {
                        reader.nextNull()
                    } else {
                        reader.beginObject()
                        while (reader.hasNext()) {
                            when (reader.nextName()) {
                                "name" -> employer = readNullableString(reader)
                                "logo_urls" -> {
                                    if (reader.peek() == JsonToken.NULL) {
                                        reader.nextNull()
                                    } else {
                                        reader.beginObject()
                                        while (reader.hasNext()) {
                                            when (reader.nextName()) {
                                                "90" -> logo = readNullableString(reader)
                                                else -> reader.skipValue()
                                            }
                                        }
                                        reader.endObject()
                                    }
                                }

                                else -> reader.skipValue()
                            }
                        }
                        reader.endObject()
                    }
                }

                "salary_range" -> {
                    if (reader.peek() == JsonToken.NULL) {
                        reader.nextNull()
                    } else {
                        reader.beginObject()
                        while (reader.hasNext()) {
                            when (reader.nextName()) {
                                "currency" -> salaryRangeCurrency = readNullableString(reader)
                                "from" -> salaryRangeFrom = readNullableInt(reader)
                                "to" -> salaryRangeTo = readNullableInt(reader)

                                else -> reader.skipValue()
                            }
                        }
                        reader.endObject()
                    }
                }

                "address" -> {
                    if (reader.peek() == JsonToken.NULL) {
                        reader.nextNull()
                    } else {
                        reader.beginObject()
                        while (reader.hasNext()) {
                            when (reader.nextName()) {
                                "city" -> city = readNullableString(reader)

                                else -> reader.skipValue()
                            }
                        }
                        reader.endObject()
                    }
                }

                else -> reader.skipValue()
            }
        }
        reader.endObject()

        return VacancyShortDto(
            id = id,
            name = name,
            employer = employer,
            logo = logo,
            salaryRangeFrom = salaryRangeFrom,
            salaryRangeTo = salaryRangeTo,
            salaryRangeCurrency = salaryRangeCurrency,
            city = city
        )
    }

    private fun readNullableString(reader: JsonReader): String? {
        return when (reader.peek()) {
            JsonToken.STRING -> reader.nextString()
            JsonToken.NULL -> {
                reader.nextNull()
                null
            }

            else -> {
                reader.skipValue()
                null
            }
        }
    }

    private fun readNullableInt(reader: JsonReader): Int? {
        return when (reader.peek()) {
            JsonToken.NUMBER -> reader.nextInt()
            JsonToken.NULL -> {
                reader.nextNull()
                null
            }

            else -> {
                reader.skipValue()
                null
            }
        }
    }
}
