package ru.practicum.android.diploma.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_vacancies")
data class VacancyDetailsEntity(
    @PrimaryKey
    val id: String,
    val timestamp: Long = System.currentTimeMillis(),
    val name: String,
    val description: String,
    val employer: String? = null,
    val logo: String? = null,
    val salaryRangeFrom: Int? = null,
    val salaryRangeTo: Int? = null,
    val salaryRangeCurrency: String? = null,
    val city: String? = null,
    val experience: String? = null,
    val keySkills: List<String>? = null,
    val workFormat: List<String>? = null
)
