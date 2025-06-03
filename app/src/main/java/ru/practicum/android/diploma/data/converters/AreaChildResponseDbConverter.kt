package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.dto.AreaChildResponseDto
import ru.practicum.android.diploma.domain.models.AreaChildResponse

class AreaChildResponseDbConverter {
    fun map(res: AreaChildResponseDto): AreaChildResponse {
        return AreaChildResponse(areas = res.areas.map { AreaDbConverter().map(it) })
    }

    fun map(res: AreaChildResponse): AreaChildResponseDto {
        return AreaChildResponseDto(areas = res.areas.map { AreaDbConverter().map(it) })
    }
}
