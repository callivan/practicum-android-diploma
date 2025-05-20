package ru.practicum.android.diploma.domain.models

import kotlinx.coroutines.flow.Flow

interface FavoriteVacanciesInteractor {
    fun getFavoriteVacancies(): Flow<List<VacancyDetails>>

    fun getFavoriteVacancyById(vacancyId: String): Flow<VacancyDetails?>

    fun insertFavoriteVacancy(vacancy: VacancyDetails): Flow<Boolean>

    fun deleteFavoriteVacancyById(vacancyId: String): Flow<Boolean>
}
