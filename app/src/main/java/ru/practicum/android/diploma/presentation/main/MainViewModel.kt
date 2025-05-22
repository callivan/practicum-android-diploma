package ru.practicum.android.diploma.presentation.main

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.ResponseStatus
import ru.practicum.android.diploma.domain.models.VacanciesInteractor
import ru.practicum.android.diploma.domain.models.VacanciesRequest
import ru.practicum.android.diploma.domain.models.VacanciesResponse
import ru.practicum.android.diploma.domain.models.VacancyShort
import ru.practicum.android.diploma.presentation.mappers.toScreenState
import ru.practicum.android.diploma.presentation.models.ScreenState
import ru.practicum.android.diploma.util.debounce

const val INPUT_DELAY = 2000L

class MainViewModel(private val vacanciesInteractor: VacanciesInteractor) : ViewModel() {
    private var pages: Int? = null

    private var prevSearchVacancies: MutableList<VacancyShort> = mutableListOf()

    private val inputDebouncer =
        debounce<VacanciesRequest>(INPUT_DELAY, viewModelScope, true) { req -> getVacancies(req) }

    private val screenState = MutableLiveData<ScreenState<VacanciesResponse>>(ScreenState.Init)

    fun getScreenState(): LiveData<ScreenState<VacanciesResponse>> = screenState

    fun getTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?, start: Int, count: Int, after: Int
            ) {
                // empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) {
                    inputDebouncer(VacanciesRequest(text = s.toString()))
                    prevSearchVacancies.clear()
                    pages = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // empty
            }
        }
    }

    fun getVacancies(req: VacanciesRequest) {
        val newPages = pages
        val newPage = req.page

        if (newPages != null && newPage != null && newPages <= newPage) return

        screenState.postValue(ScreenState.Loading)

        viewModelScope.launch(Dispatchers.IO) {
            vacanciesInteractor.getVacancies(req).collect { state ->
                when (state) {
                    is ResponseStatus.Success -> {

                        if (state.data.items.isNotEmpty()) {
                            if (prevSearchVacancies.isEmpty()) {
                                prevSearchVacancies += state.data.items

                                screenState.postValue(state.toScreenState())
                            } else {
                                val items = prevSearchVacancies

                                items += state.data.items

                                pages = state.data.pages

                                screenState.postValue(
                                    ScreenState.Success(
                                        VacanciesResponse(
                                            items = items,
                                            page = state.data.page,
                                            pages = state.data.pages,
                                            found = state.data.found
                                        )
                                    )
                                )
                            }
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
