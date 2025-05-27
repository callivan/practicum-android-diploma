package ru.practicum.android.diploma.presentation.filter.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.models.Area

class FilterPlaceViewModel : ViewModel() {
    val selectedCountry = MutableLiveData<Area?>()
    val selectedRegion = MutableLiveData<Area?>()
}

