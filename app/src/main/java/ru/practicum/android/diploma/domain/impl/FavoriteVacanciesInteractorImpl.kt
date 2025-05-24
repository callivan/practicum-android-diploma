package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.FavoriteVacanciesRepository
import ru.practicum.android.diploma.domain.models.FavoriteVacanciesInteractor
import ru.practicum.android.diploma.domain.models.VacancyDetails

class FavoriteVacanciesInteractorImpl(private val favoriteVacanciesRepository: FavoriteVacanciesRepository) :
    FavoriteVacanciesInteractor {
    override fun getFavoriteVacancies(): Flow<List<VacancyDetails>> {
        return favoriteVacanciesRepository.getFavoriteVacancies()
    }

    override fun getFavoriteVacancyById(vacancyId: String): Flow<VacancyDetails?> {
        return favoriteVacanciesRepository.getFavoriteVacancyById(vacancyId)
    }

    override fun insertFavoriteVacancy(vacancy: VacancyDetails): Flow<Boolean> {
        return favoriteVacanciesRepository.insertFavoriteVacancy(vacancy)
    }

    override fun deleteFavoriteVacancyById(vacancyId: String): Flow<Boolean> {
        return favoriteVacanciesRepository.deleteFavoriteVacancyById(vacancyId)
    }
}
