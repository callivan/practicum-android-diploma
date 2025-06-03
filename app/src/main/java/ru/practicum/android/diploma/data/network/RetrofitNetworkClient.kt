package ru.practicum.android.diploma.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.dto.RequestTypeDto
import ru.practicum.android.diploma.data.dto.ResponseStatusDto
import ru.practicum.android.diploma.util.safeApiCall

class RetrofitNetworkClient(private val headHunterApiServices: HeadHunterApiServices) :
    NetworkClient {
    override suspend fun request(dto: RequestTypeDto): ResponseStatusDto<Any> {
        return withContext(Dispatchers.IO) {
            when (dto) {
                is RequestTypeDto.RequestVacancies -> {
                    safeApiCall {
                        headHunterApiServices.getVacancies(
                            text = dto.data.text,
                            page = dto.data.page,
                            area = dto.data.area,
                            salary = dto.data.salary,
                            onlyWithSalary = dto.data.onlyWithSalary,
                            industry = dto.data.industry
                        )
                    }
                }

                is RequestTypeDto.RequestVacancy -> {
                    safeApiCall { headHunterApiServices.getVacancyById(dto.vacancyId) }
                }

                is RequestTypeDto.RequestIndustries -> {
                    safeApiCall { headHunterApiServices.getIndustries() }
                }

                is RequestTypeDto.RequestAreas -> {
                    safeApiCall { headHunterApiServices.getAreas() }
                }

                is RequestTypeDto.RequestAreaChild -> {
                    safeApiCall { headHunterApiServices.getAreaChildById(dto.areaId) }
                }
            }
        }
    }
}
