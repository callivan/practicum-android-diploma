package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.VacanciesFiltersRepository
import ru.practicum.android.diploma.domain.models.VacanciesFilters
import ru.practicum.android.diploma.domain.models.VacanciesFiltersInteractor

class VacanciesFiltersInteractorImpl(private val vacanciesFiltersRepository: VacanciesFiltersRepository) :
    VacanciesFiltersInteractor {

    override fun get(): VacanciesFilters? {
        return vacanciesFiltersRepository.get()
    }

    override fun add(data: VacanciesFilters) {
        vacanciesFiltersRepository.add(data)
    }

    override fun clean() {
        vacanciesFiltersRepository.clean()
    }
}
