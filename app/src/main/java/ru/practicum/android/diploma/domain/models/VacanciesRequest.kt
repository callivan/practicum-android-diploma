package ru.practicum.android.diploma.domain.models

data class VacanciesRequest(
    val text: String,
    val page: Int? = null,
    val area: List<String>? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean = false,
    val industry: List<String>? = null
)
