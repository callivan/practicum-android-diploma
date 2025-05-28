package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.VacanciesFilters

interface VacanciesFiltersRepository {
    fun get(): VacanciesFilters?
    fun add(data: VacanciesFilters): Unit
    fun clean(): Unit
}
