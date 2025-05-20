package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.dto.ResponseStatus
import ru.practicum.android.diploma.domain.models.VacanciesRequest
import ru.practicum.android.diploma.domain.models.VacanciesResponse

interface VacanciesRepository {
    fun getVacancies(queries: VacanciesRequest): Flow<ResponseStatus<VacanciesResponse>>
}
