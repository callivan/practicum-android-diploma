package ru.practicum.android.diploma.presentation.filter

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.AreaChildResponse
import ru.practicum.android.diploma.domain.models.AreasInteractor
import ru.practicum.android.diploma.domain.models.IndustriesInteractor
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.ResponseStatus
import ru.practicum.android.diploma.domain.models.VacanciesFilters
import ru.practicum.android.diploma.domain.models.VacanciesFiltersInteractor
import ru.practicum.android.diploma.presentation.mappers.toScreenState
import ru.practicum.android.diploma.presentation.models.ScreenState
import ru.practicum.android.diploma.util.debounce

private const val INPUT_DELAY = 2000L

private var filters: VacanciesFilters? = null

class FilterViewModel(
    private val vacanciesFiltersInteractor: VacanciesFiltersInteractor,
    private val areasInteractor: AreasInteractor,
    private val industryInteractor: IndustriesInteractor
) : ViewModel() {

    private val industries: MutableList<Industry> = mutableListOf()
    private val regions: MutableList<Area> = mutableListOf()

    private val regionsInputDebouncer = debounce<String>(INPUT_DELAY, viewModelScope, true) { text ->
        val filteredRegions = regions.filter { it.name.contains(text, ignoreCase = true) }

        if (filteredRegions.isNotEmpty()) {
            regionsScreenState.postValue(ScreenState.Success(AreaChildResponse(areas = filteredRegions)))
        } else {
            regionsScreenState.postValue(ScreenState.Empty)
        }
    }

    private val industriesInputDebouncer = debounce<String>(INPUT_DELAY, viewModelScope, true) { text ->
        val filteredIndustries = if (text.isBlank()) {
            industries
        } else {
            industries.filter { it.name.contains(text, ignoreCase = true) }
        }

        industriesScreenState.postValue(
            if (filteredIndustries.isEmpty()) {
                ScreenState.Empty
            } else {
                ScreenState.Success(filteredIndustries)
            }
        )
    }

    private val industriesScreenState = MutableLiveData<ScreenState<List<Industry>>>(ScreenState.Init)
    private val regionsScreenState = MutableLiveData<ScreenState<AreaChildResponse>>(ScreenState.Init)
    private val countriesScreenState = MutableLiveData<ScreenState<List<Area>>>(ScreenState.Init)

    fun getIndustriesScreenState(): LiveData<ScreenState<List<Industry>>> = industriesScreenState
    fun getRegionsScreenState(): LiveData<ScreenState<AreaChildResponse>> = regionsScreenState
    fun getCountriesScreenState(): LiveData<ScreenState<List<Area>>> = countriesScreenState

    fun filtersConcatenation() {
        filters = vacanciesFiltersInteractor.get()
    }

    fun filterChanged(): Boolean {
        return filters != vacanciesFiltersInteractor.get()
    }

    fun setIndustry(data: Industry?) {
        if (filters == null) {
            filters = VacanciesFilters()
        }

        filters = filters?.copy(industry = if (data != null) mutableListOf(data) else null)
    }

    fun setArea(data: MutableList<Area>? = null) {
        println(data)
        if (filters == null) {
            filters = VacanciesFilters(area = data)
        } else {
            filters = filters?.copy(area = data)
        }
    }

    fun setSalary(data: String?) {
        val isEmpty = data != null && data.isEmpty()
        val isNotEmpty = data != null && data.isNotEmpty()

        if (filters == null) {
            filters = VacanciesFilters(salary = data?.toInt())
        } else {
            filters = filters?.copy(salary = if (isEmpty) null else if (isNotEmpty) data?.toInt() else null)
        }
    }

    fun setOnlyWithSalary() {
        if (filters == null) {
            filters = VacanciesFilters(onlyWithSalary = true)
        } else {
            filters = filters?.copy(onlyWithSalary = !filters?.onlyWithSalary!!)
        }
    }

    fun getIndustriesTextWatcher(): TextWatcher {
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
                industriesInputDebouncer(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                // empty
            }
        }
    }

    fun getRegionsTextWatcher(): TextWatcher {
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
                regionsInputDebouncer(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                // empty
            }
        }
    }

    fun getIndustries() {
        industriesScreenState.postValue(ScreenState.Loading)

        viewModelScope.launch(Dispatchers.IO) {
            industryInteractor.getIndustries().collect { state ->
                when (state) {
                    is ResponseStatus.Success -> {
                        if (state.data.isNotEmpty()) {
                            industriesScreenState.postValue(ScreenState.Success(state.data))

                            industries.addAll(state.data)
                        } else {
                            industriesScreenState.postValue(ScreenState.Empty)
                        }
                    }

                    else -> industriesScreenState.postValue(state.toScreenState())
                }
            }
        }
    }

    fun getCountries() {
        countriesScreenState.postValue(ScreenState.Loading)

        viewModelScope.launch(Dispatchers.IO) {
            areasInteractor.gerAreas().collect { state ->
                when (state) {
                    is ResponseStatus.Success -> {
                        if (state.data.isNotEmpty()) {
                            countriesScreenState.postValue(ScreenState.Success(state.data))
                        } else {
                            countriesScreenState.postValue(ScreenState.Empty)
                        }
                    }

                    else -> countriesScreenState.postValue(state.toScreenState())
                }
            }
        }
    }

    fun getCountryRegions(countryId: String) {
        regionsScreenState.postValue(ScreenState.Loading)

        viewModelScope.launch(Dispatchers.IO) {
            areasInteractor.getAreaChildById(countryId).collect { state ->
                when (state) {
                    is ResponseStatus.Success -> {
                        if (state.data.areas.isNotEmpty()) {
                            regionsScreenState.postValue(ScreenState.Success(state.data))

                            regions.addAll(state.data.areas)
                        } else {
                            regionsScreenState.postValue(ScreenState.Empty)
                        }
                    }

                    else -> regionsScreenState.postValue(state.toScreenState())
                }
            }
        }
    }

    fun getFilters(): VacanciesFilters? {
        return filters
    }

    fun addFilters() {
        if (filters == null) return

        vacanciesFiltersInteractor.add(filters!!)
    }

    fun cleanFilters() {
        filters = VacanciesFilters()
        vacanciesFiltersInteractor.clean()
    }
}
