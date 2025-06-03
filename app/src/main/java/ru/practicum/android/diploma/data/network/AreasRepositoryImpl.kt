package ru.practicum.android.diploma.data.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.converters.AreaDbConverter
import ru.practicum.android.diploma.data.dto.AreaDto
import ru.practicum.android.diploma.data.dto.RequestTypeDto
import ru.practicum.android.diploma.data.dto.ResponseStatusDto
import ru.practicum.android.diploma.domain.api.AreasRepository
import ru.practicum.android.diploma.domain.mappers.toResponseStatus
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.ResponseStatus

class AreasRepositoryImpl(
    private val networkClient: NetworkClient,
    private val areaDbConverter: AreaDbConverter
) : AreasRepository {
    override fun getAreas(): Flow<ResponseStatus<List<Area>>> = flow {
        val res = networkClient.request(RequestTypeDto.RequestAreas) as ResponseStatusDto<List<AreaDto>>

        val data = if (res is ResponseStatusDto.Success) {
            ResponseStatus.Success(res.data.map { areaDbConverter.map(it) })
        } else {
            res.toResponseStatus() as ResponseStatus<List<Area>>
        }

        emit(data)
    }
}
