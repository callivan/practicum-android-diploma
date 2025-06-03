package ru.practicum.android.diploma.domain.models

import kotlinx.coroutines.flow.Flow

interface AreasInteractor {
    fun gerAreas(): Flow<ResponseStatus<List<Area>>>
}
