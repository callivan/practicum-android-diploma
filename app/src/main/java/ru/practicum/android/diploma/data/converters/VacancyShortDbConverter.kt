package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.dto.VacancyShortDto
import ru.practicum.android.diploma.domain.models.VacancyShort

class VacancyShortDbConverter {
    fun map(vacancy: VacancyShortDto): VacancyShort {
        return VacancyShort(
            id = vacancy.id,
            name = vacancy.name,
            employer = vacancy.employer,
            logo = vacancy.logo,
            salaryRangeFrom = vacancy.salaryRangeFrom,
            salaryRangeTo = vacancy.salaryRangeTo,
            salaryRangeCurrency = vacancy.salaryRangeCurrency,
            city = vacancy.city,
        )
    }

    fun map(vacancy: VacancyShort): VacancyShortDto {
        return VacancyShortDto(
            id = vacancy.id,
            name = vacancy.name,
            employer = vacancy.employer,
            logo = vacancy.logo,
            salaryRangeFrom = vacancy.salaryRangeFrom,
            salaryRangeTo = vacancy.salaryRangeTo,
            salaryRangeCurrency = vacancy.salaryRangeCurrency,
            city = vacancy.city,
        )
    }
}
