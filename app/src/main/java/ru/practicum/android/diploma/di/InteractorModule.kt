package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.api.FavoriteVacanciesRepository
import ru.practicum.android.diploma.domain.api.VacanciesRepository
import ru.practicum.android.diploma.domain.impl.FavoriteVacanciesInteractorImpl
import ru.practicum.android.diploma.domain.impl.VacanciesInteractorImpl
import ru.practicum.android.diploma.domain.models.FavoriteVacanciesInteractor
import ru.practicum.android.diploma.domain.models.VacanciesInteractor

val interactorModule = module {
    factory<VacanciesInteractor> {
        VacanciesInteractorImpl(get<VacanciesRepository>())
    }

    factory<FavoriteVacanciesInteractor> {
        FavoriteVacanciesInteractorImpl(get<FavoriteVacanciesRepository>())
    }
}
