package ru.practicum.android.diploma.domain.models

data class VacanciesRequest(
    val text: String,
    val page: Int? = null,
    val area: String? = null,
    val salary: Int? = null,
    val only_with_salary: Boolean = false,
    val professional_role: String? = null
)
