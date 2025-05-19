package ru.practicum.android.diploma.data.dto

data class VacanciesResponseDto(
    val items: List<VacancyShortDto>,
    val found: Int,
    val page: Int,
    val pages: Int
)
