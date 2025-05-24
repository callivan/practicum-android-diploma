package ru.practicum.android.diploma.presentation.models

sealed interface ScreenState<out T> {
    object Init : ScreenState<Nothing>
    object Loading : ScreenState<Nothing>
    data class Success<T>(val data: T) : ScreenState<T>
    data class Insert(val isInserted: Boolean) : ScreenState<Nothing>
    data class Delete(val isDeleted: Boolean) : ScreenState<Nothing>
    object NotFound : ScreenState<Nothing>
    object InternalServerError : ScreenState<Nothing>
    object NetworkError : ScreenState<Nothing>
    object UnknownError : ScreenState<Nothing>
    object Empty : ScreenState<Nothing>
}
