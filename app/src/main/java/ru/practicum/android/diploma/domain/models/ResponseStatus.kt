package ru.practicum.android.diploma.domain.models

sealed interface ResponseStatus<out T> {
    data class Success<T>(val data: T) : ResponseStatus<T>
    object BadRequest : ResponseStatus<Nothing>
    object Forbidden : ResponseStatus<Nothing>
    object NotFound : ResponseStatus<Nothing>
    object InternalServerError : ResponseStatus<Nothing>
    data class NetworkError(val error: Throwable) : ResponseStatus<Nothing>
    data class UnknownError(val error: Throwable?) : ResponseStatus<Nothing>
}
