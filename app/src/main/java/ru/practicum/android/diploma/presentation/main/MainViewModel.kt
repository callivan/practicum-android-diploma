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
import ru.practicum.android.diploma.domain.models.VacanciesFiltersInteractor
import ru.practicum.android.diploma.domain.models.VacanciesInteractor
import ru.practicum.android.diploma.domain.models.VacanciesRequest
import ru.practicum.android.diploma.domain.models.VacanciesResponse
import ru.practicum.android.diploma.domain.models.VacancyShort
import ru.practicum.android.diploma.presentation.mappers.toScreenState
import ru.practicum.android.diploma.presentation.models.ScreenState
import ru.practicum.android.diploma.util.debounce
import kotlin.String

private const val INPUT_DELAY = 2000L

class MainViewModel(
    private val vacanciesInteractor: VacanciesInteractor,
    private val vacanciesFiltersInteractor: VacanciesFiltersInteractor
) : ViewModel() {
    private var pages: Int? = null
    private var vacancy: String? = null

    private var prevSearchVacancies: MutableList<VacancyShort> = mutableListOf()

    private val inputDebouncer = debounce<VacanciesRequest>(INPUT_DELAY, viewModelScope, true) { req ->
        getVacancies(req)
    }

    private val screenState = MutableLiveData<ScreenState<VacanciesResponse>>(ScreenState.Init)

    fun getScreenState(): LiveData<ScreenState<VacanciesResponse>> = screenState

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
                if (vacancy != s.toString()) {
                    inputDebouncer(VacanciesRequest(text = s.toString()))
                    prevSearchVacancies.clear()
                    pages = null
                    vacancy = s.toString()
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // empty
            }
        }
    }

    private fun isPageEnded(page: Int?): Boolean {
        val newPages = pages
        val newPage = page

        return newPages != null && newPage != null && newPages <= newPage
    }

    private fun setSuccessState(state: VacanciesResponse): VacanciesResponse {
        if (state.items.isNotEmpty()) {
            if (prevSearchVacancies.isEmpty()) {
                prevSearchVacancies += state.items
            } else {
                val items = prevSearchVacancies

                items += state.items

                pages = state.pages

                return VacanciesResponse(
                    items = items,
                    page = state.page,
                    pages = state.pages,
                    found = state.found
                )
            }
        }

        return state
    }

    fun isFiltersSelected(): Boolean {
        val filters = vacanciesFiltersInteractor.get()
        val isFilters = filters != null
        val isSalary = filters?.salary != null
        val isArea = filters?.area != null
        val isIndustry = filters?.industry != null

        return isFilters && (isArea || isSalary || filters.onlyWithSalary || isIndustry)
    }

    fun loadMore(page: Int?) {
        getVacancies(VacanciesRequest(text = vacancy ?: "", page = page))
    }

    private fun getVacancies(req: VacanciesRequest) {
        val filters = vacanciesFiltersInteractor.get()

        val request = VacanciesRequest(
            text = req.text,
            page = req.page,
            area = filters?.area?.map { it.id },
            salary = filters?.salary,
            onlyWithSalary = filters?.onlyWithSalary == true,
            industry = filters?.industry?.map { it.id }
        )

        if (request.text.isEmpty()) {
            screenState.postValue(ScreenState.Init)
            return
        }

        if (isPageEnded(req.page)) return

        screenState.postValue(ScreenState.Loading)

        viewModelScope.launch(Dispatchers.IO) {
            vacanciesInteractor.getVacancies(request).collect { state ->
                when (state) {
                    is ResponseStatus.Success -> {
                        if (state.data.items.isNotEmpty()) {
                            screenState.postValue(ScreenState.Success(setSuccessState(state.data)))
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
