package ru.practicum.android.diploma.presentation.main

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.ResponseStatus
import ru.practicum.android.diploma.domain.models.VacanciesInteractor
import ru.practicum.android.diploma.domain.models.VacanciesRequest
import ru.practicum.android.diploma.presentation.main.mappers.toMainScreenState
import ru.practicum.android.diploma.presentation.main.models.MainScreenState
import ru.practicum.android.diploma.util.debounce

const val INPUT_DELAY = 500L

class MainViewModel(private val vacanciesInteractor: VacanciesInteractor) : ViewModel() {
    private val screenState = MutableLiveData<MainScreenState>(MainScreenState.Empty)

    private val inputDebouncer = debounce<String>(INPUT_DELAY, viewModelScope, true) { text -> getVacancies(text) }

    fun getScreenState(): LiveData<MainScreenState> = screenState

    fun getTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) {
                    inputDebouncer(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // empty
            }
        }
    }

    fun getVacancies(text: String) {
        screenState.postValue(MainScreenState.Loading)

        viewModelScope.launch {
            vacanciesInteractor.getVacancies(VacanciesRequest(text = text))
                .collect { state ->
                    when (state) {
                        is ResponseStatus.Success -> {
                            screenState.postValue(
                                if (state.data.items.isNotEmpty()) state.toMainScreenState() else MainScreenState.Empty
                            )
                        }

                        else -> screenState.postValue(state.toMainScreenState())
                    }
                }
        }
    }
}
