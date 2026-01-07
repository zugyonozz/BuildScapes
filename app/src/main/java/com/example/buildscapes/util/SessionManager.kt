package com.example.buildscapes.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("buildscapes_session", Context.MODE_PRIVATE)

    var isFirstRun: Boolean
        get() = prefs.getBoolean("is_first_run", true)
        set(value) = prefs.edit { putBoolean("is_first_run", value) }

    var isLoggedIn: Boolean
        get() = prefs.getBoolean("is_logged_in", false)
        set(value) = prefs.edit { putBoolean("is_logged_in", value) }

    fun logout() {
        isLoggedIn = false
    }
}