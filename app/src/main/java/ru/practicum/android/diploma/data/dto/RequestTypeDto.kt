package ru.practicum.android.diploma.data.dto

sealed interface RequestTypeDto {
    data class RequestVacancies(val data: VacanciesRequestDto) : RequestTypeDto
    data class RequestVacancy(val vacancyId: String) : RequestTypeDto
    object RequestIndustries : RequestTypeDto
    object RequestAreas : RequestTypeDto
}
