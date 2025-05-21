package ru.practicum.android.diploma.data.interceptors

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import ru.practicum.android.diploma.util.isConnected
import java.io.IOException

class InternetAvailableInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected(context)) {
            throw IOException("No internet connection")
        }

        return chain.proceed(chain.request())
    }
}
