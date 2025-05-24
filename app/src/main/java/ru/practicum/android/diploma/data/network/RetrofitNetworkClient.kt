package ru.practicum.android.diploma.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.dto.ResponseStatusDto
import ru.practicum.android.diploma.data.dto.VacanciesRequestDto
import ru.practicum.android.diploma.data.dto.toQueryMap
import ru.practicum.android.diploma.domain.models.VacancyRequest
import ru.practicum.android.diploma.util.safeApiCall

class RetrofitNetworkClient(private val headHunterApiServices: HeadHunterApiServices) :
    NetworkClient {
    override suspend fun request(dto: Any): ResponseStatusDto<Any> {
        return withContext(Dispatchers.IO) {
            when (dto) {
                is VacanciesRequestDto -> {
                    safeApiCall {
                        headHunterApiServices.getVacancies(
                            VacanciesRequestDto(
                                text = dto.text,
                                page = dto.page,
                                area = dto.area,
                                salary = dto.salary,
                                onlyWithSalary = dto.onlyWithSalary,
                                professionalRole = dto.professionalRole
                            ).toQueryMap()
                        )
                    }
                }

                is VacancyRequest -> {
                    safeApiCall { headHunterApiServices.getVacancyById(dto.vacancyId) }
                }

                else -> {
                    ResponseStatusDto.UnknownError(null)
                }
            }
        }
    }
}
