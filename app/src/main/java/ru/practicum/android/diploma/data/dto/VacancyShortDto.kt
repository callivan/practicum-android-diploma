package ru.practicum.android.diploma.data.dto

data class VacancyShortDto(
    val id: String,
    val name: String,
    val employer: String? = null,
    val logo: String? = null,
    val salaryRangeFrom: Int? = null,
    val salaryRangeTo: Int? = null,
    val salaryRangeCurrency: String? = null,
    val address: String? = null,
)
