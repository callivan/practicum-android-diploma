package ru.practicum.android.diploma.data.filters

import ru.practicum.android.diploma.data.SharedPrefs
import ru.practicum.android.diploma.data.dto.AreaDto
import ru.practicum.android.diploma.data.dto.IndustryDto
import ru.practicum.android.diploma.data.dto.VacanciesFiltersDto
import ru.practicum.android.diploma.domain.api.VacanciesFiltersRepository
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.VacanciesFilters

class VacanciesFiltersRepositoryImpl(private val vacanciesFiltersPrefs: SharedPrefs<VacanciesFiltersDto>) :
    VacanciesFiltersRepository {

    override fun get(): VacanciesFilters? {
        val filters = vacanciesFiltersPrefs.get()

        return if (filters != null) {
            VacanciesFilters(
                area = filters.area?.map { Area(id = it.id, name = it.name, areas = emptyList()) }?.toMutableList(),
                salary = filters.salary,
                onlyWithSalary = filters.onlyWithSalary,
                industry = filters.industry?.map { Industry(id = it.id, name = it.name) },
                isApply = filters.isApply
            )
        } else {
            null
        }
    }

    override fun add(data: VacanciesFilters) {
        vacanciesFiltersPrefs.add(
            VacanciesFiltersDto(
                area = data.area?.map { AreaDto(id = it.id, name = it.name, areas = emptyList()) },
                salary = data.salary,
                onlyWithSalary = data.onlyWithSalary,
                industry = data.industry?.map { IndustryDto(id = it.id, name = it.name) },
                isApply = data.isApply
            )
        )
    }

    override fun clean() {
        vacanciesFiltersPrefs.clean()
    }
}
