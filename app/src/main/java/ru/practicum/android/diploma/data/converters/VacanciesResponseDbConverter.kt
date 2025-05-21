package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.dto.VacanciesResponseDto
import ru.practicum.android.diploma.domain.models.VacanciesResponse

class VacanciesResponseDbConverter {
    fun map(response: VacanciesResponseDto): VacanciesResponse {
        return VacanciesResponse(
            items = response.items.map { vacancy -> VacancyShortDbConverter().map(vacancy) },
            page = response.page,
            pages = response.pages,
            found = response.found
        )
    }

    fun map(response: VacanciesResponse): VacanciesResponseDto {
        return VacanciesResponseDto(
            items = response.items.map { vacancy -> VacancyShortDbConverter().map(vacancy) },
            page = response.page,
            pages = response.pages,
            found = response.found
        )
    }
}
