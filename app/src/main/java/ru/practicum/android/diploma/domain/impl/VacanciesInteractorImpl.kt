package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.VacanciesRepository
import ru.practicum.android.diploma.domain.models.ResponseStatus
import ru.practicum.android.diploma.domain.models.VacanciesInteractor
import ru.practicum.android.diploma.domain.models.VacanciesRequest
import ru.practicum.android.diploma.domain.models.VacanciesResponse
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.domain.models.VacancyRequest

class VacanciesInteractorImpl(private val vacanciesRepository: VacanciesRepository) :
    VacanciesInteractor {
    override fun getVacancies(queries: VacanciesRequest): Flow<ResponseStatus<VacanciesResponse>> {
        return vacanciesRepository.getVacancies(queries)
    }

    override fun getVacancyById(data: VacancyRequest): Flow<ResponseStatus<VacancyDetails>> {
        return vacanciesRepository.getVacancyById(data)
    }
}
