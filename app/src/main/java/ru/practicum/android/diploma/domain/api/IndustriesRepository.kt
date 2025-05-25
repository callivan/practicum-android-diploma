package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.ResponseStatus

interface IndustriesRepository {
    fun getIndustries(): Flow<ResponseStatus<List<Industry>>>
}
