package ru.practicum.android.diploma.presentation.filter.industry

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.IndustriesInteractor
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.ResponseStatus
import ru.practicum.android.diploma.presentation.mappers.toScreenState
import ru.practicum.android.diploma.presentation.models.ScreenState
import ru.practicum.android.diploma.util.debounce

private const val INPUT_DELAY = 2000L

class FilterIndustryViewModel(private val industryInteractor: IndustriesInteractor) : ViewModel() {

    private val industries: MutableList<Industry> = mutableListOf()

    private val inputDebouncer = debounce<String>(INPUT_DELAY, viewModelScope, true) { text ->
        val filteredIndustries = industries.filter { it.name.contains(text, ignoreCase = true) }

        screenState.postValue(ScreenState.Success(filteredIndustries))
    }

    private val screenState = MutableLiveData<ScreenState<List<Industry>>>(ScreenState.Init)

    fun getScreenState(): LiveData<ScreenState<List<Industry>>> = screenState

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

    fun getIndustries() {
        screenState.postValue(ScreenState.Loading)

        viewModelScope.launch(Dispatchers.IO) {
            industryInteractor.getIndustries().collect { state ->
                when (state) {
                    is ResponseStatus.Success -> {
                        if (state.data.isNotEmpty()) {
                            screenState.postValue(ScreenState.Success(state.data))

                            industries.addAll(state.data)
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
