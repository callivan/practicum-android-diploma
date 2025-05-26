package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.domain.models.AreasInteractor
import ru.practicum.android.diploma.domain.models.FavoriteVacanciesInteractor
import ru.practicum.android.diploma.domain.models.IndustriesInteractor
import ru.practicum.android.diploma.domain.models.VacanciesInteractor
import ru.practicum.android.diploma.presentation.favorite.FavoriteViewModule
import ru.practicum.android.diploma.presentation.filter.FilterViewModel
import ru.practicum.android.diploma.presentation.filter.industry.FilterIndustryViewModel
import ru.practicum.android.diploma.presentation.filter.place.FilterCountryViewModel
import ru.practicum.android.diploma.presentation.filter.place.FilterRegionViewModel
import ru.practicum.android.diploma.presentation.main.MainViewModel
import ru.practicum.android.diploma.presentation.vacancy.VacancyViewModel

val viewModelModule = module {
    viewModel<MainViewModel> {
        MainViewModel(get<VacanciesInteractor>())
    }

    viewModel<VacancyViewModel> {
        VacancyViewModel(get<VacanciesInteractor>(), get<FavoriteVacanciesInteractor>())
    }

    viewModel<FavoriteViewModule> {
        FavoriteViewModule(get<FavoriteVacanciesInteractor>())
    }

    viewModel<FilterIndustryViewModel> {
        FilterIndustryViewModel(get<IndustriesInteractor>())
    }

    viewModel<FilterCountryViewModel> {
        FilterCountryViewModel(get<AreasInteractor>())
    }

    viewModel<FilterRegionViewModel> {
        FilterRegionViewModel(get<AreasInteractor>())
    }

    viewModel<FilterViewModel> {
        FilterViewModel()
    }
}
