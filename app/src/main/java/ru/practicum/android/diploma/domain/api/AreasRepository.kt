package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.ResponseStatus

interface AreasRepository {
    fun getAreas(): Flow<ResponseStatus<List<Area>>>
}
