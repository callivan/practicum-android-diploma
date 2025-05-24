package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.converters.VacanciesResponseDbConverter
import ru.practicum.android.diploma.data.converters.VacancyDetailsDbConverter
import ru.practicum.android.diploma.data.db.AppDb
import ru.practicum.android.diploma.data.db.FavoriteVacanciesRepositoryImpl
import ru.practicum.android.diploma.data.network.VacanciesRepositoryImpl
import ru.practicum.android.diploma.domain.api.FavoriteVacanciesRepository
import ru.practicum.android.diploma.domain.api.VacanciesRepository

val repositoryModule = module {
    factory<VacanciesRepository> {
        VacanciesRepositoryImpl(
            get<NetworkClient>(),
            get<VacancyDetailsDbConverter>(),
            get<VacanciesResponseDbConverter>(),
        )
    }

    factory<FavoriteVacanciesRepository> {
        FavoriteVacanciesRepositoryImpl(get<AppDb>(), get<VacancyDetailsDbConverter>())
    }
}
