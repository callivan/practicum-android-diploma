package ru.practicum.android.diploma.presentation.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.models.SelectedFilters
import ru.practicum.android.diploma.presentation.models.ScreenState

class FilterViewModel: ViewModel() {
    private val state = MutableLiveData<ScreenState<SelectedFilters>>()
    fun getState(): LiveData<ScreenState<SelectedFilters>> = state

    init {
        state.postValue(ScreenState.Success(
                SelectedFilters(
                    "place",
                    "industry",
                    12345,
                    true
                )
            )
        )
    }
}
