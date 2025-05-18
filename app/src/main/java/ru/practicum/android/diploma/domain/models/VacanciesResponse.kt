package ru.practicum.android.diploma.domain.models

data class VacanciesResponse(val items: List<VacancyShort>, val found: Int, val page: Int, val pages: Int)
