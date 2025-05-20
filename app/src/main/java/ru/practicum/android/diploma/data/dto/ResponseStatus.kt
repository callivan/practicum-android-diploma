package ru.practicum.android.diploma.data.dto

sealed interface ResponseStatus<out T> {
    data class Content<T>(val data: T) : ResponseStatus<T>
    object Empty : ResponseStatus<Nothing>
    data class Error(val err: Throwable) : ResponseStatus<Nothing>
}
