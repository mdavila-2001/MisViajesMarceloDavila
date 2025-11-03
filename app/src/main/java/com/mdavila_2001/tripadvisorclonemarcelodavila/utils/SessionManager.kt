package com.mdavila_2001.tripadvisorclonemarcelodavila.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    companion object {
        private const val USERNAME = "username"
    }

    fun saveUsername(username: String) {
        val editor = prefs.edit()
        editor.putString(USERNAME, username)
        editor.apply()
    }

    fun getUsername(): String? {
        return prefs.getString(USERNAME, null)
    }

    fun isLoggedIn(): Boolean {
        return getUsername() != null
    }

    fun logout() {
        val editor = prefs.edit()
        editor.remove(USERNAME)
        editor.apply()
    }
}