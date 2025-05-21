package ru.practicum.android.diploma.presentation.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.FavoriteVacanciesInteractor
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.presentation.models.ScreenState

class FavoriteViewModule(private val favoriteVacanciesInteractor: FavoriteVacanciesInteractor) : ViewModel() {
    private val screenState = MutableLiveData<ScreenState<List<VacancyDetails>>>(ScreenState.Empty)

    fun getScreenState(): LiveData<ScreenState<List<VacancyDetails>>> = screenState

    fun getFavoriteVacancies() {
        screenState.postValue(ScreenState.Loading)

        viewModelScope.launch {
            favoriteVacanciesInteractor.getFavoriteVacancies().collect { state ->
                if (state.isNotEmpty()) {
                    screenState.postValue(ScreenState.Success(state))
                } else {
                    screenState.postValue(ScreenState.Empty)
                }
            }
        }
    }
}
