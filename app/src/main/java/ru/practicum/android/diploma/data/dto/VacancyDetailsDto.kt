package ru.practicum.android.diploma.data.dto

data class VacancyDetailsDto(
    val id: String,
    val name: String,
    val description: String,
    var employer: String? = null,
    val logo: String? = null,
    val salaryRangeFrom: Int? = null,
    val salaryRangeTo: Int? = null,
    val salaryRangeCurrency: String? = null,
    val city: String? = null,
    val experience: String? = null,
    val keySkills: List<String>? = null,
    val workFormat: List<String>? = null
)
