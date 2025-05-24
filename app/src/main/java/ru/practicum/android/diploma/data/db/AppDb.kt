package ru.practicum.android.diploma.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.practicum.android.diploma.data.converters.ArrayTypeConverter
import ru.practicum.android.diploma.data.db.dao.FavoriteVacanciesDao
import ru.practicum.android.diploma.data.db.entities.VacancyDetailsEntity

@Database(entities = [VacancyDetailsEntity::class], version = 1)
@TypeConverters(ArrayTypeConverter::class)
abstract class AppDb : RoomDatabase() {
    abstract fun favoriteVacanciesDao(): FavoriteVacanciesDao
}
