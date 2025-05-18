package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.JsonAdapter
import ru.practicum.android.diploma.data.typeAdapters.VacanciesResponseTypeAdapter

@JsonAdapter(VacanciesResponseTypeAdapter::class)
data class VacanciesResponseDto(
    val items: List<VacancyShortDto>, val found: Int, val page: Int, val pages: Int
)
