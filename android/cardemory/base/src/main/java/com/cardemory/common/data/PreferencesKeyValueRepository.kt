package com.cardemory.common.data

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class PreferencesKeyValueRepository
@Inject constructor(
    context: Context
) : KeyValueRepository {

    private val preferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    override suspend fun readBoolean(key: String) =
        preferences.getBoolean(key, false)

    override suspend fun readString(key: String) =
        preferences.getString(key, "")!!

    override suspend fun <V> writeValue(key: String, value: V) =
        preferences.edit()
            .putValue(key, value)
            .commit()

    private fun <V> SharedPreferences.Editor.putValue(
        key: String, value: V
    ): SharedPreferences.Editor {
        return when (value) {
            is Boolean -> putBoolean(key, value)
            is Int -> putInt(key, value)
            is Long -> putLong(key, value)
            is String -> putString(key, value)
            is Set<*> -> @Suppress("UNCHECKED_CAST")
            putStringSet(key, value as Set<String>)
            is Float -> putFloat(key, value)
            else -> throw IllegalStateException("Unexpected value type: $value")
        }
    }

    companion object {
        private const val SHARED_PREFS_NAME = "app-prefs"
    }
}