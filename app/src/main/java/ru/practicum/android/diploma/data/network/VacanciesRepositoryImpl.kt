package ru.practicum.android.diploma.data.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.dto.ResponseStatus
import ru.practicum.android.diploma.domain.api.VacanciesRepository
import ru.practicum.android.diploma.domain.models.VacanciesRequest
import ru.practicum.android.diploma.domain.models.VacanciesResponse
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.domain.models.VacancyRequest

class VacanciesRepositoryImpl(private val networkClient: NetworkClient) : VacanciesRepository {
    override fun getVacancies(queries: VacanciesRequest): Flow<ResponseStatus<VacanciesResponse>> =
        flow {
            val res = networkClient.request(queries)
            emit(res as ResponseStatus<VacanciesResponse>)
        }

    override fun getVacancyById(data: VacancyRequest): Flow<ResponseStatus<VacancyDetails>> = flow {
        val res = networkClient.request(data)
        emit(res as ResponseStatus<VacancyDetails>)
    }
}
