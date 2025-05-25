package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.ResponseStatus
import ru.practicum.android.diploma.domain.models.VacanciesRequest
import ru.practicum.android.diploma.domain.models.VacanciesResponse
import ru.practicum.android.diploma.domain.models.VacancyDetails

interface VacanciesRepository {
    fun getVacancies(queries: VacanciesRequest): Flow<ResponseStatus<VacanciesResponse>>

    fun getVacancyById(vacancyId: String): Flow<ResponseStatus<VacancyDetails>>

    fun getIndustries(): Flow<ResponseStatus<List<Industry>>>
}
