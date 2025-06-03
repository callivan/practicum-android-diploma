package ru.practicum.android.diploma.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.practicum.android.diploma.data.dto.AreaDto
import ru.practicum.android.diploma.data.dto.IndustryDto
import ru.practicum.android.diploma.data.dto.VacanciesResponseDto
import ru.practicum.android.diploma.data.dto.VacancyDetailsDto

interface HeadHunterApiServices {
    @GET("/vacancies")
    suspend fun getVacancies(
        @Query("text") text: String,
        @Query("page") page: Int? = null,
        @Query("area") area: List<String>? = null,
        @Query("salary") salary: Int? = null,
        @Query("onlyWithSalary") onlyWithSalary: Boolean = false,
        @Query("industry") industry: List<String>? = null
    ): Response<VacanciesResponseDto>

    @GET("/vacancies/{id}")
    suspend fun getVacancyById(@Path("id") vacancyId: String): Response<VacancyDetailsDto>

    @GET("/industries")
    suspend fun getIndustries(): Response<List<IndustryDto>>

    @GET("/areas")
    suspend fun getAreas(): Response<List<AreaDto>>
}
