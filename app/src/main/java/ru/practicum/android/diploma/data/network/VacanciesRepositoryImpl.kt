package ru.practicum.android.diploma.data.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.converters.VacanciesResponseDbConverter
import ru.practicum.android.diploma.data.converters.VacancyDetailsDbConverter
import ru.practicum.android.diploma.data.dto.ResponseStatusDto
import ru.practicum.android.diploma.data.dto.VacanciesRequestDto
import ru.practicum.android.diploma.data.dto.VacanciesResponseDto
import ru.practicum.android.diploma.data.dto.VacancyDetailsDto
import ru.practicum.android.diploma.domain.api.VacanciesRepository
import ru.practicum.android.diploma.domain.mappers.toResponseStatus
import ru.practicum.android.diploma.domain.models.ResponseStatus
import ru.practicum.android.diploma.domain.models.VacanciesRequest
import ru.practicum.android.diploma.domain.models.VacanciesResponse
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.domain.models.VacancyRequest

class VacanciesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val vacancyDetailsDbConverter: VacancyDetailsDbConverter,
    private val vacanciesResponseDbConverter: VacanciesResponseDbConverter
) : VacanciesRepository {
    override fun getVacancies(queries: VacanciesRequest): Flow<ResponseStatus<VacanciesResponse>> = flow {
        val res = networkClient.request(
            VacanciesRequestDto(
                text = queries.text,
                page = queries.page,
                area = queries.area,
                salary = queries.salary,
                onlyWithSalary = queries.onlyWithSalary,
                professionalRole = queries.professionalRole
            )
        ) as ResponseStatusDto<VacanciesResponseDto>

        val data =
            if (res is ResponseStatusDto.Success) {
                ResponseStatus.Success(
                    vacanciesResponseDbConverter.map(res.data)
                )
            } else {
                res.toResponseStatus() as ResponseStatus<VacanciesResponse>
            }

        emit(data)
    }

    override fun getVacancyById(data: VacancyRequest): Flow<ResponseStatus<VacancyDetails>> = flow {
        val res = networkClient.request(data) as ResponseStatusDto<VacancyDetailsDto>

        val data =
            if (res is ResponseStatusDto.Success) {
                ResponseStatus.Success(
                    vacancyDetailsDbConverter.map(res.data)
                )
            } else {
                res.toResponseStatus() as ResponseStatus<VacancyDetails>
            }

        emit(data)
    }
}
