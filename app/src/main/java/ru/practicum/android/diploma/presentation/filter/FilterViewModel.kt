package ru.practicum.android.diploma.presentation.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.models.SelectedFilters
import ru.practicum.android.diploma.presentation.models.ScreenState

class FilterViewModel : ViewModel() {

    companion object {
        val EMPTY_FILTER = SelectedFilters("", "", null, false)
        val TEST_FILTER = SelectedFilters("place", "", 12345, false)
    }

    private val state = MutableLiveData<ScreenState<SelectedFilters>>()
    fun getState(): LiveData<ScreenState<SelectedFilters>> = state

    var currentFilters = TEST_FILTER

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
        currentFilters = EMPTY_FILTER
        state.postValue(ScreenState.Success(currentFilters))
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
