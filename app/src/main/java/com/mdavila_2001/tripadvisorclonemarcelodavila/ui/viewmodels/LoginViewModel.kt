package com.mdavila_2001.tripadvisorclonemarcelodavila.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mdavila_2001.tripadvisorclonemarcelodavila.utils.SessionManager

class LoginViewModel(application: Application): AndroidViewModel(application) {
    private val sessionManager = SessionManager(application)

    fun onLoginClicked(username: String): Boolean {
        if(username.isBlank()) {
            return false
        }
        sessionManager.saveUsername(username.trim())
        return true
    }
}