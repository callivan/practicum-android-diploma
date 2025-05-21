package ru.practicum.android.diploma.presentation.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.domain.models.FavoriteVacanciesInteractor
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.presentation.models.ScreenState

class FavoriteViewModule(private val favoriteVacanciesInteractor: FavoriteVacanciesInteractor) : ViewModel() {
    private val screenState = MutableLiveData<ScreenState<List<VacancyDetails>>>(ScreenState.Empty)

    fun getScreenState(): LiveData<ScreenState<List<VacancyDetails>>> = screenState

    fun getFavoriteVacancies() {
        screenState.postValue(ScreenState.Loading)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                favoriteVacanciesInteractor.getFavoriteVacancies().collect { vacancies ->
                    withContext(Dispatchers.Main) {
                        if (vacancies.isNotEmpty()) {
                            screenState.postValue(ScreenState.Success(vacancies))
                        } else {
                            screenState.postValue(ScreenState.Empty)
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    screenState.postValue(ScreenState.NetworkError)
                }
            }
        }
    }
}
