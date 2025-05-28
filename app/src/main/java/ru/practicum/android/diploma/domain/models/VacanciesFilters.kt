package ru.practicum.android.diploma.domain.models

data class VacanciesFilters(
    val area: MutableList<Area>? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean = false,
    val industry: List<Industry>? = null
)
