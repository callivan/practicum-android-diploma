package ru.practicum.android.diploma.domain.models

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.dto.ResponseStatus

interface VacanciesInteractor {
    fun getVacancies(queries: VacanciesRequest): Flow<ResponseStatus<VacanciesResponse>>
}
