package ru.practicum.android.diploma.domain.models

data class VacancyDetails(
    val id: String,
    val name: String,
    val description: String,
    val employer: String? = null,
    val logo: String? = null,
    val salaryRangeFrom: Int? = null,
    val salaryRangeTo: Int? = null,
    val salaryRangeCurrency: String? = null,
    val city: String? = null,
    val experience: String? = null,
    val keySkills: List<String>? = null,
    val workFormat: List<String>? = null
)
