package com.example.managespending.preference

import android.content.Context
import android.content.SharedPreferences

object MyPreferences {
    private lateinit var prefs: SharedPreferences
    private const val PREFS_NAME = "shared_preferences"
    const val PREF_CURRENT_THEME = "pref_current_theme"
    const val PREF_CURRENT_LANGUAGE = "pref_current_language"


    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun read(key: String, value: String): String? {
        return prefs.getString(key, value)
    }

    fun write(key: String, value: String) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putString(key, value)
            commit()
        }
    }

    fun read(key: String, value: Long): Long {
        return prefs.getLong(key, value)
    }

    fun write(key: String, value: Long) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putLong(key, value)
            commit()
        }
    }

    fun read(key: String, value: Int): Int {
        return prefs.getInt(key, value)
    }

    fun write(key: String, value: Int) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putInt(key, value)
            commit()
        }
    }
}