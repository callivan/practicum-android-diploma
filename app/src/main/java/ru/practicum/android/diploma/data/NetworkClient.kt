package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.dto.ResponseStatusDto

interface NetworkClient {
    suspend fun request(dto: Any): ResponseStatusDto<Any>
}
