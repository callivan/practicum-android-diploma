package ru.practicum.android.diploma.util

import retrofit2.Response
import ru.practicum.android.diploma.data.dto.ResponseStatusDto
import java.io.IOException
import java.net.HttpURLConnection

suspend fun <T> safeApiCall(
    apiCall: suspend () -> Response<T>
): ResponseStatusDto<T> {
    return try {
        val response = apiCall()

        when (response.code()) {
            HttpURLConnection.HTTP_OK -> {
                val body = response.body()
                if (body != null) {
                    ResponseStatusDto.Success(body)
                } else {
                    ResponseStatusDto.UnknownError(null)
                }
            }

            HttpURLConnection.HTTP_BAD_REQUEST -> ResponseStatusDto.BadRequest
            HttpURLConnection.HTTP_FORBIDDEN -> ResponseStatusDto.Forbidden
            HttpURLConnection.HTTP_NOT_FOUND -> ResponseStatusDto.NotFound
            HttpURLConnection.HTTP_INTERNAL_ERROR -> ResponseStatusDto.InternalServerError
            else -> ResponseStatusDto.UnknownError(null)
        }
    } catch (e: IOException) {
        ResponseStatusDto.NetworkError(e)
    }
}
