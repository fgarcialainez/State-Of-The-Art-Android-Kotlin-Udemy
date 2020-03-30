@file:Suppress("DEPRECATION")

package com.fgarcialainez.androidkotlincourse.utils

import android.content.Context

class SharedPreferencesHelper(context: Context) {
    private val PREF_NAME = "PREF_NAME"
    private val PREF_API_KEY = "PREF_API_KEY"

    private val preferences = context.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveApiKey(key: String) {
        preferences.edit().putString(PREF_API_KEY, key).apply()
    }

    fun getApiKey() = preferences.getString(PREF_API_KEY, null)
}