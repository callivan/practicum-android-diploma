package ru.practicum.android.diploma.domain.models

data class VacancyShort(
    val id: String,
    val name: String,
    val logo: String? = null,
    val employer: String? = null,
    val salaryRangeFrom: Int? = null,
    val salaryRangeTo: Int? = null,
    val salaryRangeCurrency: String? = null,
    val address: String? = null,
)
