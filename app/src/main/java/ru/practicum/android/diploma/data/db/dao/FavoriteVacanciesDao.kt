package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ru.practicum.android.diploma.data.db.entities.VacancyDetailsEntity

@Dao
interface FavoriteVacanciesDao {
    @Query("SELECT * FROM favorite_vacancies ORDER BY timestamp DESC")
    fun getFavoriteVacancies(): List<VacancyDetailsEntity>

    @Query("SELECT * FROM favorite_vacancies WHERE id = :vacancyId")
    fun getFavoriteVacancyById(vacancyId: String): VacancyDetailsEntity?

    @Transaction
    fun insertFavoriteVacancy(vacancy: VacancyDetailsEntity): Boolean {
        val isExists = exists(vacancyId = vacancy.id)

        if (isExists) {
            return false
        }

        insert(vacancy)

        return true
    }

    @Transaction
    fun deleteFavoriteVacancyById(vacancyId: String): Boolean {
        val isExists = exists(vacancyId = vacancyId)

        if (isExists) {
            delete(vacancyId)

            return true
        }

        return false
    }

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_vacancies WHERE id = :vacancyId)")
    fun exists(vacancyId: String): Boolean

    @Insert(entity = VacancyDetailsEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insert(vacancy: VacancyDetailsEntity)

    @Query("DELETE FROM favorite_vacancies WHERE id = :vacancyId")
    fun delete(vacancyId: String)
}
