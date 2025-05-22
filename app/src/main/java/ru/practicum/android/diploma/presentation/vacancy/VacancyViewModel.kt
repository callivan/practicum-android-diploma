package ru.practicum.android.diploma.presentation.vacancy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.FavoriteVacanciesInteractor
import ru.practicum.android.diploma.domain.models.VacanciesInteractor
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.domain.models.VacancyRequest
import ru.practicum.android.diploma.presentation.mappers.toScreenState
import ru.practicum.android.diploma.presentation.models.ScreenState

class VacancyViewModel(
    private val vacanciesInteractor: VacanciesInteractor,
    private val favoriteVacanciesInteractor: FavoriteVacanciesInteractor
) : ViewModel() {
    private val screenState = MutableLiveData<ScreenState<VacancyDetails>>(ScreenState.Empty)

    fun getScreenState(): LiveData<ScreenState<VacancyDetails>> = screenState

    fun getVacancyById(vacancyId: String, isInternetAvailable: Boolean) {
        screenState.postValue(ScreenState.Loading)

        viewModelScope.launch {
            if (isInternetAvailable) {
                vacanciesInteractor.getVacancyById(VacancyRequest(vacancyId))
                    .collect { state ->
                        screenState.postValue(state.toScreenState())
                    }

            } else {
                favoriteVacanciesInteractor.getFavoriteVacancyById(vacancyId).collect { state ->
                    screenState.postValue(if (state != null) ScreenState.Success(state) else ScreenState.Empty)
                }
            }
        }
    }

    fun insertInFavorite(vacancy: VacancyDetails) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteVacanciesInteractor.insertFavoriteVacancy(vacancy).collect { state ->
                screenState.postValue(ScreenState.Insert(state))
            }
        }
    }

    fun deleteFromFavoriteById(vacancyId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteVacanciesInteractor.deleteFavoriteVacancyById(vacancyId).collect { state ->
                screenState.postValue(ScreenState.Delete(state))
            }
        }
    }
}
