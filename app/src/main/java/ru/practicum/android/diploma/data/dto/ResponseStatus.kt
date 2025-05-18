package ru.practicum.android.diploma.data.dto

sealed interface ResponseStatus<out T> {
    data class Content<T>(val data: T) : ResponseStatus<T>
    object Empty : ResponseStatus<Nothing>
    object Error : ResponseStatus<Nothing>
}
