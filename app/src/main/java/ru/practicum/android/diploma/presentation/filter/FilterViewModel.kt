package ru.practicum.android.diploma.presentation.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.models.SelectedFilters
import ru.practicum.android.diploma.presentation.models.ScreenState

class FilterViewModel: ViewModel() {
    private val state = MutableLiveData<ScreenState<SelectedFilters>>()
    fun getState(): LiveData<ScreenState<SelectedFilters>> = state

    var currentFilters = SelectedFilters("place", "industry", 12345, false)

    init {
        state.postValue(ScreenState.Success(currentFilters))
    }

    fun onClickShowNoSalary() {
        currentFilters = SelectedFilters(
            currentFilters.place,
            currentFilters.industry,
            currentFilters.salary,
            !currentFilters.showNoSalary
        )
        state.postValue(ScreenState.Success(currentFilters))
    }

    fun clearFilters() {
        // сбрасываем фильтры
    }

    fun setFilters() {
        // сохраняем выбранные фильтры
    }

    fun setPlace() {
        // задаем место работы
    }

    fun setIndustry() {
        // задаем отрасль
    }
}
