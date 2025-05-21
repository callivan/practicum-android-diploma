package ru.practicum.android.diploma.domain.models

import kotlinx.coroutines.flow.Flow

interface VacanciesInteractor {
    fun getVacancies(queries: VacanciesRequest): Flow<ResponseStatus<VacanciesResponse>>

    fun getVacancyById(data: VacancyRequest): Flow<ResponseStatus<VacancyDetails>>
}
