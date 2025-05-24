package ru.practicum.android.diploma.data.db

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.converters.VacancyDetailsDbConverter
import ru.practicum.android.diploma.domain.api.FavoriteVacanciesRepository
import ru.practicum.android.diploma.domain.models.VacancyDetails

class FavoriteVacanciesRepositoryImpl(
    private val appDb: AppDb,
    private val favoriteVacanciesDbConverter: VacancyDetailsDbConverter
) : FavoriteVacanciesRepository {
    override fun getFavoriteVacancies(): Flow<List<VacancyDetails>> = flow {
        val res = appDb.favoriteVacanciesDao().getFavoriteVacancies()
        emit(res.map { vacancy -> favoriteVacanciesDbConverter.map(vacancy) })
    }

    override fun getFavoriteVacancyById(vacancyId: String): Flow<VacancyDetails?> = flow {
        val res = appDb.favoriteVacanciesDao().getFavoriteVacancyById(vacancyId)
        emit(if (res != null) favoriteVacanciesDbConverter.map(res) else null)
    }

    override fun insertFavoriteVacancy(vacancy: VacancyDetails): Flow<Boolean> = flow {
        emit(appDb.favoriteVacanciesDao().insertFavoriteVacancy(favoriteVacanciesDbConverter.map(vacancy)))
    }

    override fun deleteFavoriteVacancyById(vacancyId: String): Flow<Boolean> = flow {
        emit(appDb.favoriteVacanciesDao().deleteFavoriteVacancyById(vacancyId))
    }
}
