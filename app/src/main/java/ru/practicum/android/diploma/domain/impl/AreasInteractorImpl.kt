package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.AreasRepository
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.AreasInteractor
import ru.practicum.android.diploma.domain.models.ResponseStatus

class AreasInteractorImpl(private val areasRepository: AreasRepository) :
    AreasInteractor {
    override fun gerAreas(): Flow<ResponseStatus<List<Area>>> {
        return areasRepository.getAreas()
    }
}
