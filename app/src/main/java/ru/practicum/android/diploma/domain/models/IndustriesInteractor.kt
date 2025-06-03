package ru.practicum.android.diploma.domain.models

import kotlinx.coroutines.flow.Flow

interface IndustriesInteractor {
    fun getIndustries(): Flow<ResponseStatus<List<Industry>>>
}
