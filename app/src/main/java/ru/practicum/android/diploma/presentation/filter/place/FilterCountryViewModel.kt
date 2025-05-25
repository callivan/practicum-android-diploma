package ru.practicum.android.diploma.presentation.filter.place

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.AreasInteractor
import ru.practicum.android.diploma.domain.models.ResponseStatus
import ru.practicum.android.diploma.presentation.mappers.toScreenState
import ru.practicum.android.diploma.presentation.models.ScreenState

class FilterCountryViewModel(private val areasInteractor: AreasInteractor) : ViewModel() {

    private val screenState = MutableLiveData<ScreenState<List<Area>>>(ScreenState.Init)

    fun getScreenState(): LiveData<ScreenState<List<Area>>> = screenState

    fun getCountries() {
        screenState.postValue(ScreenState.Loading)

        viewModelScope.launch(Dispatchers.IO) {
            areasInteractor.gerAreas().collect { state ->
                when (state) {
                    is ResponseStatus.Success -> {
                        if (state.data.isNotEmpty()) {
                            screenState.postValue(ScreenState.Success(state.data))
                        } else {
                            screenState.postValue(ScreenState.Empty)
                        }
                    }

                    else -> screenState.postValue(state.toScreenState())
                }
            }
        }
    }
}
