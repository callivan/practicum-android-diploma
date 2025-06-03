package ru.practicum.android.diploma.data.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.converters.IndustryDbConverter
import ru.practicum.android.diploma.data.dto.IndustryDto
import ru.practicum.android.diploma.data.dto.RequestTypeDto
import ru.practicum.android.diploma.data.dto.ResponseStatusDto
import ru.practicum.android.diploma.domain.api.IndustriesRepository
import ru.practicum.android.diploma.domain.mappers.toResponseStatus
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.ResponseStatus

class IndustriesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val industryDbConverter: IndustryDbConverter,
) : IndustriesRepository {
    override fun getIndustries(): Flow<ResponseStatus<List<Industry>>> = flow {
        val res = networkClient.request(RequestTypeDto.RequestIndustries) as ResponseStatusDto<List<IndustryDto>>

        val data = if (res is ResponseStatusDto.Success) {
            ResponseStatus.Success(res.data.map { industryDbConverter.map(it) })
        } else {
            res.toResponseStatus() as ResponseStatus<List<Industry>>
        }

        emit(data)
    }
}
