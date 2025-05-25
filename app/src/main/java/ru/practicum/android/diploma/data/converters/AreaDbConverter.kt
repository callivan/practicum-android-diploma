package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.dto.AreaDto
import ru.practicum.android.diploma.domain.models.Area

class AreaDbConverter {
    fun map(area: AreaDto): Area {
        return Area(id = area.id, name = area.name)
    }

    fun map(area: Area): AreaDto {
        return AreaDto(id = area.id, name = area.name)
    }
}
