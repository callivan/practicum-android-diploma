package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.db.entities.VacancyDetailsEntity
import ru.practicum.android.diploma.data.dto.VacancyDetailsDto
import ru.practicum.android.diploma.domain.models.VacancyDetails

class VacancyDetailsDbConverter {
    fun map(vacancy: VacancyDetailsDto): VacancyDetails {
        return VacancyDetails(
            id = vacancy.id,
            name = vacancy.name,
            description = vacancy.description,
            alternateUrl = vacancy.alternateUrl,
            employer = vacancy.employer,
            logo = vacancy.logo,
            salaryRangeFrom = vacancy.salaryRangeFrom,
            salaryRangeTo = vacancy.salaryRangeTo,
            salaryRangeCurrency = vacancy.salaryRangeCurrency,
            address = vacancy.address,
            experience = vacancy.experience,
            keySkills = vacancy.keySkills,
            workFormat = vacancy.workFormat
        )
    }

    fun map(vacancy: VacancyDetails): VacancyDetailsEntity {
        return VacancyDetailsEntity(
            id = vacancy.id,
            name = vacancy.name,
            description = vacancy.description,
            alternateUrl = vacancy.alternateUrl,
            employer = vacancy.employer,
            logo = vacancy.logo,
            salaryRangeFrom = vacancy.salaryRangeFrom,
            salaryRangeTo = vacancy.salaryRangeTo,
            salaryRangeCurrency = vacancy.salaryRangeCurrency,
            address = vacancy.address,
            experience = vacancy.experience,
            keySkills = vacancy.keySkills,
            workFormat = vacancy.workFormat
        )
    }

    fun map(vacancy: VacancyDetailsEntity): VacancyDetails {
        return VacancyDetails(
            id = vacancy.id,
            name = vacancy.name,
            description = vacancy.description,
            alternateUrl = vacancy.alternateUrl,
            employer = vacancy.employer,
            logo = vacancy.logo,
            salaryRangeFrom = vacancy.salaryRangeFrom,
            salaryRangeTo = vacancy.salaryRangeTo,
            salaryRangeCurrency = vacancy.salaryRangeCurrency,
            address = vacancy.address,
            experience = vacancy.experience,
            keySkills = vacancy.keySkills,
            workFormat = vacancy.workFormat
        )
    }
}
