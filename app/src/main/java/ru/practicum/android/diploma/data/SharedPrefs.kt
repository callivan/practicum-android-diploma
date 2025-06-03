package ru.practicum.android.diploma.data

interface SharedPrefs<out T> {
    fun get(): T?
    fun <T> add(data: T): Unit
    fun clean(): Unit
}
