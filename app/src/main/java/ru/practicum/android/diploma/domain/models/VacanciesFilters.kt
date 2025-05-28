package ru.practicum.android.diploma.domain.models

data class VacanciesFilters(
    var area: MutableList<Area>? = null,
    var salary: Int? = null,
    var onlyWithSalary: Boolean = false,
    var industry: List<Industry>? = null
)
