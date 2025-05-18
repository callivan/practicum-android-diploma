package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.dto.ResponseStatus

interface NetworkClient {
    suspend fun request(dto: Any): ResponseStatus<Any>
}
