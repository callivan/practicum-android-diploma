package ru.practicum.android.diploma.presentation.vacancy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.VacanciesInteractor
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.domain.models.VacancyRequest
import ru.practicum.android.diploma.presentation.mappers.toScreenState
import ru.practicum.android.diploma.presentation.models.ScreenState

class VacancyViewModel(private val vacanciesInteractor: VacanciesInteractor) : ViewModel() {
    private val screenState = MutableLiveData<ScreenState<VacancyDetails>>(ScreenState.Empty)

    fun getScreenState(): LiveData<ScreenState<VacancyDetails>> = screenState

    fun getVacancy(vacancyId: String) {
        screenState.postValue(ScreenState.Loading)

        viewModelScope.launch {
            vacanciesInteractor.getVacancyById(VacancyRequest(vacancyId))
                .collect { state ->
                    println(state)
                    screenState.postValue(state.toScreenState())
                }
        }
    }
}
