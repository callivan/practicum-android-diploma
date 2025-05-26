package ru.practicum.android.diploma.domain.models

data class SelectedFilters(
    val place: String,
    val industry: String,
    val salary: Int,
    val showNoSalary: Boolean
)
