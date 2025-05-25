package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.dto.IndustryDto
import ru.practicum.android.diploma.domain.models.Industry

class IndustryDbConverter {
    fun map(industry: IndustryDto): Industry {
        return Industry(id = industry.id, name = industry.name)
    }

    fun map(industry: Industry): IndustryDto {
        return IndustryDto(id = industry.id, name = industry.name)
    }
}
