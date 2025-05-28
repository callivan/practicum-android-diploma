package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.SharedPrefs
import ru.practicum.android.diploma.data.converters.AreaDbConverter
import ru.practicum.android.diploma.data.converters.IndustryDbConverter
import ru.practicum.android.diploma.data.converters.VacanciesResponseDbConverter
import ru.practicum.android.diploma.data.converters.VacancyDetailsDbConverter
import ru.practicum.android.diploma.data.db.AppDb
import ru.practicum.android.diploma.data.db.FavoriteVacanciesRepositoryImpl
import ru.practicum.android.diploma.data.dto.VacanciesFiltersDto
import ru.practicum.android.diploma.data.filters.VacanciesFiltersRepositoryImpl
import ru.practicum.android.diploma.data.network.AreasRepositoryImpl
import ru.practicum.android.diploma.data.network.IndustriesRepositoryImpl
import ru.practicum.android.diploma.data.network.VacanciesRepositoryImpl
import ru.practicum.android.diploma.domain.api.AreasRepository
import ru.practicum.android.diploma.domain.api.FavoriteVacanciesRepository
import ru.practicum.android.diploma.domain.api.IndustriesRepository
import ru.practicum.android.diploma.domain.api.VacanciesFiltersRepository
import ru.practicum.android.diploma.domain.api.VacanciesRepository

val repositoryModule = module {
    factory<VacanciesRepository> {
        VacanciesRepositoryImpl(
            get<NetworkClient>(),
            get<VacancyDetailsDbConverter>(),
            get<VacanciesResponseDbConverter>(),
        )
    }

    factory<IndustriesRepository> {
        IndustriesRepositoryImpl(get<NetworkClient>(), get<IndustryDbConverter>())
    }

    factory<AreasRepository> {
        AreasRepositoryImpl(get<NetworkClient>(), get<AreaDbConverter>())
    }

    factory<FavoriteVacanciesRepository> {
        FavoriteVacanciesRepositoryImpl(get<AppDb>(), get<VacancyDetailsDbConverter>())
    }

    factory<VacanciesFiltersRepository> {
        VacanciesFiltersRepositoryImpl(get<SharedPrefs<VacanciesFiltersDto>>())
    }
}
