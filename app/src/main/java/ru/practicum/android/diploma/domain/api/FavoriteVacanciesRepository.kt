package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancyDetails

interface FavoriteVacanciesRepository {
    fun getFavoriteVacancies(): Flow<List<VacancyDetails>>

    fun getFavoriteVacancyById(vacancyId: String): Flow<VacancyDetails?>

    fun insertFavoriteVacancy(vacancy: VacancyDetails): Flow<Boolean>

    fun deleteFavoriteVacancyById(vacancyId: String): Flow<Boolean>
}
