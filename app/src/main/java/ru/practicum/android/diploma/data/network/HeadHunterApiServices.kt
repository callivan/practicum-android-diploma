package ru.practicum.android.diploma.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.dto.VacanciesResponseDto
import ru.practicum.android.diploma.data.dto.VacancyDetailsDto

interface HeadHunterApiServices {
    @GET("/vacancies")
    suspend fun getVacancies(@QueryMap(encoded = true) queries: Map<String, String?>): Response<VacanciesResponseDto>

    @GET("/vacancies/{id}")
    suspend fun getVacancyById(@Path("id") vacancyId: String): Response<VacancyDetailsDto>
}
