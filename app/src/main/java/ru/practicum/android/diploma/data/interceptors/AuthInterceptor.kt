package ru.practicum.android.diploma.data.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import ru.practicum.android.diploma.BuildConfig

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = BuildConfig.HH_ACCESS_TOKEN
        val request = chain.request().newBuilder().addHeader("Authorization", "Bearer $token").build()

        return chain.proceed(request)
    }
}
