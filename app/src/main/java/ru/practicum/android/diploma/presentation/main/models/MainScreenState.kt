package ru.practicum.android.diploma.presentation.main.models

import ru.practicum.android.diploma.domain.models.VacanciesResponse

sealed interface MainScreenState {
    object Loading : MainScreenState
    data class Success(val data: VacanciesResponse) : MainScreenState
    object NotFound : MainScreenState
    object InternalServerError : MainScreenState
    object NetworkError : MainScreenState
    object UnknownError : MainScreenState
    object Empty : MainScreenState
}
