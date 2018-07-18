package com.synexoit.weatherapp.data.manager

import android.content.SharedPreferences

class SharedPreferencesManager(private val sharedPreferences: SharedPreferences) {

    fun putValue(key: String, value: Any) {
        val sp = sharedPreferences.edit()

        when (value) {
            is String -> sp.putString(key, value)
            is Int -> sp.putInt(key, value)
            is Boolean -> sp.putBoolean(key, value)
            is Double -> sp.putLong(key, java.lang.Double.doubleToRawLongBits(value))
            is Long -> sp.putLong(key, value)
            is Float -> sp.putFloat(key, value)
        }

        sp.apply()
    }

    fun getString(key: String): String = sharedPreferences.getString(key, "")
    fun getInt(key: String): Int = sharedPreferences.getInt(key, 0)
    fun getBoolean(key: String): Boolean = sharedPreferences.getBoolean(key, false)
    fun getDouble(key: String): Double = java.lang.Double.longBitsToDouble(getLong(key))
    fun getLong(key: String): Long = sharedPreferences.getLong(key, 0)
    fun getFloat(key: String): Float = sharedPreferences.getFloat(key, 0f)

    fun clear() = sharedPreferences.edit().clear().apply()
}