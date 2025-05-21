package ru.practicum.android.diploma.presentation.mappers

import ru.practicum.android.diploma.domain.models.ResponseStatus
import ru.practicum.android.diploma.presentation.models.ScreenState

fun <T> ResponseStatus<T>.toScreenState(): ScreenState<T> = when (this) {
    is ResponseStatus.Success -> ScreenState.Success(data)
    is ResponseStatus.NetworkError -> ScreenState.NetworkError
    is ResponseStatus.InternalServerError -> ScreenState.InternalServerError
    is ResponseStatus.NotFound -> ScreenState.NotFound

    else -> ScreenState.UnknownError
}
