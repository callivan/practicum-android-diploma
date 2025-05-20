package ru.practicum.android.diploma.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.dto.ResponseStatus
import ru.practicum.android.diploma.data.dto.VacanciesRequestDto
import ru.practicum.android.diploma.data.dto.toQueryMap
import ru.practicum.android.diploma.domain.models.VacanciesRequest

class RetrofitNetworkClient(private val headHunterApiServices: HeadHunterApiServices) :
    NetworkClient {
    override suspend fun request(dto: Any): ResponseStatus<Any> {
        if (dto !is VacanciesRequest) {
            return ResponseStatus.Empty
        }

        return withContext(Dispatchers.IO) {
            try {
                val data = headHunterApiServices.getVacancies(
                    VacanciesRequestDto(
                        text = dto.text,
                        page = dto.page,
                        area = dto.area,
                        salary = dto.salary,
                        onlyWithSalary = dto.onlyWithSalary,
                        professionalRole = dto.professionalRole
                    ).toQueryMap()
                )

                if (data.items.isNotEmpty()) {
                    ResponseStatus.Content(data)
                } else {
                    ResponseStatus.Empty
                }
            } catch (e: HttpException) {
                ResponseStatus.Error(e)
            }
        }
    }
}
