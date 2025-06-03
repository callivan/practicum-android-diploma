package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.api.AreasRepository
import ru.practicum.android.diploma.domain.api.FavoriteVacanciesRepository
import ru.practicum.android.diploma.domain.api.IndustriesRepository
import ru.practicum.android.diploma.domain.api.VacanciesFiltersRepository
import ru.practicum.android.diploma.domain.api.VacanciesRepository
import ru.practicum.android.diploma.domain.impl.AreasInteractorImpl
import ru.practicum.android.diploma.domain.impl.FavoriteVacanciesInteractorImpl
import ru.practicum.android.diploma.domain.impl.IndustriesInteractorImpl
import ru.practicum.android.diploma.domain.impl.VacanciesFiltersInteractorImpl
import ru.practicum.android.diploma.domain.impl.VacanciesInteractorImpl
import ru.practicum.android.diploma.domain.models.AreasInteractor
import ru.practicum.android.diploma.domain.models.FavoriteVacanciesInteractor
import ru.practicum.android.diploma.domain.models.IndustriesInteractor
import ru.practicum.android.diploma.domain.models.VacanciesFiltersInteractor
import ru.practicum.android.diploma.domain.models.VacanciesInteractor

val interactorModule = module {
    factory<VacanciesInteractor> {
        VacanciesInteractorImpl(get<VacanciesRepository>())
    }

    factory<FavoriteVacanciesInteractor> {
        FavoriteVacanciesInteractorImpl(get<FavoriteVacanciesRepository>())
    }

    factory<IndustriesInteractor> {
        IndustriesInteractorImpl(get<IndustriesRepository>())
    }

    factory<AreasInteractor> {
        AreasInteractorImpl(get<AreasRepository>())
    }

    factory<VacanciesFiltersInteractor> {
        VacanciesFiltersInteractorImpl(get<VacanciesFiltersRepository>())
    }
}
