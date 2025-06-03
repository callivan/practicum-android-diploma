package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.IndustriesRepository
import ru.practicum.android.diploma.domain.models.IndustriesInteractor
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.ResponseStatus

class IndustriesInteractorImpl(private val industriesRepository: IndustriesRepository) :
    IndustriesInteractor {
    override fun getIndustries(): Flow<ResponseStatus<List<Industry>>> {
        return industriesRepository.getIndustries()
    }
}
