package ru.practicum.android.diploma.util

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject

fun JsonElement.asSafeJsonObject(): JsonObject? =
    takeIf { !it.isJsonNull && it.isJsonObject }?.asJsonObject

fun JsonObject.safeGetString(key: String): String? =
    get(key)?.takeIf { !it.isJsonNull }?.asString

fun JsonObject.safeGetInt(key: String): Int? =
    get(key)?.takeIf { !it.isJsonNull }?.asInt

fun JsonObject.safeGetLong(key: String): Long? =
    get(key)?.takeIf { !it.isJsonNull }?.asLong

fun JsonObject.safeGetJsonObject(key: String): JsonObject? =
    get(key)?.asSafeJsonObject()

fun JsonObject.safeGetJsonArray(key: String): JsonArray? =
    get(key)?.takeIf { !it.isJsonNull && it.isJsonArray }?.asJsonArray
