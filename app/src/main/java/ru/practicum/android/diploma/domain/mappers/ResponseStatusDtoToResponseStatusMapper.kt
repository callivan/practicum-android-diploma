package ru.practicum.android.diploma.domain.mappers

import ru.practicum.android.diploma.data.dto.ResponseStatusDto
import ru.practicum.android.diploma.domain.models.ResponseStatus

fun <T> ResponseStatusDto<T>.toResponseStatus(): ResponseStatus<T> = when (this) {
    is ResponseStatusDto.Success -> ResponseStatus.Success(data)
    is ResponseStatusDto.NotFound -> ResponseStatus.NotFound
    is ResponseStatusDto.NetworkError -> ResponseStatus.NetworkError(error)
    is ResponseStatusDto.InternalServerError -> ResponseStatus.InternalServerError
    is ResponseStatusDto.BadRequest -> ResponseStatus.BadRequest
    is ResponseStatusDto.Forbidden -> ResponseStatus.Forbidden
    is ResponseStatusDto.UnknownError -> ResponseStatus.UnknownError(error)
}
