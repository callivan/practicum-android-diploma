package ru.practicum.android.diploma.data.dto

sealed interface ResponseStatusDto<out T> {
    data class Success<T>(val data: T) : ResponseStatusDto<T>
    object BadRequest : ResponseStatusDto<Nothing>
    object Forbidden : ResponseStatusDto<Nothing>
    object NotFound : ResponseStatusDto<Nothing>
    object InternalServerError : ResponseStatusDto<Nothing>
    data class NetworkError(val error: Throwable) : ResponseStatusDto<Nothing>
    data class UnknownError(val error: Throwable?) : ResponseStatusDto<Nothing>
}
