package ru.practicum.android.diploma.data.filters

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit
import com.google.gson.Gson
import ru.practicum.android.diploma.data.SharedPrefs
import ru.practicum.android.diploma.data.dto.VacanciesFiltersDto

class VacanciesFilters(private val context: Context) : SharedPrefs<VacanciesFiltersDto> {
    private val sharedPrefs = context.getSharedPreferences(VACANCIES_FILTERS_PREFS, MODE_PRIVATE)

    init {
        val json = sharedPrefs.getString(
            VACANCIES_FILTERS_PREFS,
            null
        )

        if (json == null) {
            add(VacanciesFiltersDto())
        }
    }

    override fun <VacanciesFiltersDto> add(filters: VacanciesFiltersDto) {
        val json = Gson().toJson(filters)

        sharedPrefs.edit() {
            putString(VACANCIES_FILTERS_PREFS, json)
        }
    }

    override fun get(): VacanciesFiltersDto? {
        val json = sharedPrefs.getString(
            VACANCIES_FILTERS_PREFS,
            null
        )

        var filters: VacanciesFiltersDto? = null

        if (json != null) {
            filters = Gson().fromJson<VacanciesFiltersDto>(json, VacanciesFiltersDto::class.java)
        }

        return filters
    }

    override fun clean() {
        println("CLEAN")
        add(VacanciesFiltersDto())
    }

    companion object {
        const val VACANCIES_FILTERS_PREFS = "VACANCIES_FILTERS_PREFS"
    }
}
