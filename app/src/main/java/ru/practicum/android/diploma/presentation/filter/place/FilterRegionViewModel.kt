package ru.practicum.android.diploma.presentation.filter.place

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
import ru.practicum.android.diploma.domain.models.ResponseStatus
import ru.practicum.android.diploma.presentation.mappers.toScreenState
import ru.practicum.android.diploma.presentation.models.ScreenState
import ru.practicum.android.diploma.util.debounce

private const val INPUT_DELAY = 2000L

class FilterRegionViewModel(private val areasInteractor: AreasInteractor) : ViewModel() {

    private val regions: MutableList<Area> = mutableListOf()

    private val inputDebouncer = debounce<String>(INPUT_DELAY, viewModelScope, true) { text ->
        val filteredRegions = regions.filter { it.name.contains(text, ignoreCase = true) }

        screenState.postValue(ScreenState.Success(AreaChildResponse(areas = filteredRegions)))
    }

    private val screenState = MutableLiveData<ScreenState<AreaChildResponse>>(ScreenState.Init)

    fun getScreenState(): LiveData<ScreenState<AreaChildResponse>> = screenState

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
                inputDebouncer(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                // empty
            }
        }
    }

    fun getCountryRegions(countryId: String) {
        screenState.postValue(ScreenState.Loading)

        viewModelScope.launch(Dispatchers.IO) {
            areasInteractor.getAreaChildById(countryId).collect { state ->
                when (state) {
                    is ResponseStatus.Success -> {
                        if (state.data.areas.isNotEmpty()) {
                            screenState.postValue(ScreenState.Success(state.data))

                            regions.addAll(state.data.areas)
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
