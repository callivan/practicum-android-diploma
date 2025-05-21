package ru.practicum.android.diploma.di

import androidx.room.Room
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.consts.Consts
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.converters.FavoriteVacanciesDbConverter
import ru.practicum.android.diploma.data.db.AppDb
import ru.practicum.android.diploma.data.deserializers.VacanciesResponseDeserializer
import ru.practicum.android.diploma.data.deserializers.VacancyResponseDeserializer
import ru.practicum.android.diploma.data.dto.VacanciesResponseDto
import ru.practicum.android.diploma.data.dto.VacancyDetailsDto
import ru.practicum.android.diploma.data.interceptors.AuthInterceptor
import ru.practicum.android.diploma.data.network.HeadHunterApiServices
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient

val dataModule = module {
    factory<FavoriteVacanciesDbConverter> { FavoriteVacanciesDbConverter() }

    single<AppDb> {
        Room.databaseBuilder(androidContext(), AppDb::class.java, "database").build()
    }

    single<HeadHunterApiServices> {
        val authInterceptor = OkHttpClient.Builder().addInterceptor(AuthInterceptor()).build()

        val responseDeserializer =
            GsonBuilder().registerTypeAdapter(VacanciesResponseDto::class.java, VacanciesResponseDeserializer())
                .registerTypeAdapter(VacancyDetailsDto::class.java, VacancyResponseDeserializer())
                .create()

        Retrofit.Builder().baseUrl(Consts.HH_API_BASE_URL).client(authInterceptor)
            .addConverterFactory(GsonConverterFactory.create(responseDeserializer)).build()
            .create<HeadHunterApiServices>(HeadHunterApiServices::class.java)
    }

    single<NetworkClient> {
        RetrofitNetworkClient(get<HeadHunterApiServices>())
    }
}
