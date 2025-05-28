package ru.practicum.android.diploma.data.dto

data class VacanciesFiltersDto(
    val area: List<AreaDto>? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean = false,
    val industry: List<IndustryDto>? = null
)
