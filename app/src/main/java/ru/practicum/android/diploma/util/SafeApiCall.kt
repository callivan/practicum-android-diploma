package ru.practicum.android.diploma.util

import retrofit2.Response
import ru.practicum.android.diploma.consts.ResponseCode
import ru.practicum.android.diploma.data.dto.ResponseStatusDto
import java.io.IOException

suspend fun <T> safeApiCall(
    apiCall: suspend () -> Response<T>
): ResponseStatusDto<T> {
    return try {
        val response = apiCall()

        when (response.code()) {
            ResponseCode.SUCCESS -> {
                val body = response.body()
                if (body != null) {
                    ResponseStatusDto.Success(body)
                } else {
                    ResponseStatusDto.UnknownError(null)
                }
            }

            ResponseCode.BAD_REQUEST -> ResponseStatusDto.BadRequest
            ResponseCode.FORBIDDEN -> ResponseStatusDto.Forbidden
            ResponseCode.NOT_FOUND -> ResponseStatusDto.NotFound
            ResponseCode.INTERNAL_SERVER_ERROR -> ResponseStatusDto.InternalServerError
            else -> ResponseStatusDto.UnknownError(null)
        }
    } catch (e: IOException) {
        ResponseStatusDto.NetworkError(e)
    }
}
