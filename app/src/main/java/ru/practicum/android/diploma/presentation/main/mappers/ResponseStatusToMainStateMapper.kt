package ru.practicum.android.diploma.presentation.main.mappers

import ru.practicum.android.diploma.domain.models.ResponseStatus
import ru.practicum.android.diploma.domain.models.VacanciesResponse
import ru.practicum.android.diploma.presentation.main.models.MainScreenState

fun ResponseStatus<VacanciesResponse>.toMainScreenState(): MainScreenState = when (this) {
    is ResponseStatus.Success -> MainScreenState.Success(data)
    is ResponseStatus.NetworkError -> MainScreenState.NetworkError
    is ResponseStatus.InternalServerError -> MainScreenState.InternalServerError
    is ResponseStatus.NotFound -> MainScreenState.NotFound

    else -> MainScreenState.UnknownError
}
