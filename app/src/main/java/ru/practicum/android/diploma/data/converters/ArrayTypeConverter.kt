package ru.practicum.android.diploma.data.converters

import androidx.room.TypeConverter

class ArrayTypeConverter {
    @TypeConverter
    fun fromString(value: String?): List<String> {
        return value?.split(",")?.toList() ?: emptyList()
    }

    @TypeConverter
    fun toString(array: List<String>?): String {
        return array?.joinToString(",") ?: ""
    }
}
