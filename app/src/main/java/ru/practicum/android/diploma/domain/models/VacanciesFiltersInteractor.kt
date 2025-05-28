package ru.practicum.android.diploma.domain.models

interface VacanciesFiltersInteractor {
    fun get(): VacanciesFilters?
    fun add(data: VacanciesFilters): Unit
    fun clean(): Unit
}
