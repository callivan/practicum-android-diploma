package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.dto.VacanciesResponseDto

interface HeadHunterApiServices {
    @GET("/vacancies")
    suspend fun getVacancies(@QueryMap(encoded = true) queries: Map<String, String?>): VacanciesResponseDto
}
