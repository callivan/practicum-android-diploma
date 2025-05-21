package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.domain.models.VacanciesInteractor
import ru.practicum.android.diploma.presentation.main.MainViewModel
import ru.practicum.android.diploma.presentation.vacancy.VacancyViewModel

val viewModelModule = module {
    viewModel<MainViewModel> {
        MainViewModel(get<VacanciesInteractor>())
    }

    viewModel<VacancyViewModel> {
        VacancyViewModel(get<VacanciesInteractor>())
    }
}
