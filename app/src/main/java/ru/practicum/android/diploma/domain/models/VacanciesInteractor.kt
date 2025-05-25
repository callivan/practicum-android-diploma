package ru.practicum.android.diploma.domain.models

import kotlinx.coroutines.flow.Flow

interface VacanciesInteractor {
    fun getVacancies(queries: VacanciesRequest): Flow<ResponseStatus<VacanciesResponse>>

    fun getVacancyById(vacancyId: String): Flow<ResponseStatus<VacancyDetails>>

    fun getIndustries(): Flow<ResponseStatus<List<Industry>>>

    fun gerAreas(): Flow<ResponseStatus<List<Area>>>
}
