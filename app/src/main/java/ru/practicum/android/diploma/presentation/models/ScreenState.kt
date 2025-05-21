package ru.practicum.android.diploma.presentation.models

sealed interface ScreenState<out T> {
    object Loading : ScreenState<Nothing>
    data class Success<T>(val data: T) : ScreenState<T>
    object NotFound : ScreenState<Nothing>
    object InternalServerError : ScreenState<Nothing>
    object NetworkError : ScreenState<Nothing>
    object UnknownError : ScreenState<Nothing>
    object Empty : ScreenState<Nothing>
}
