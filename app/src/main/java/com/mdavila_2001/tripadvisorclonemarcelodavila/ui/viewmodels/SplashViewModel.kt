package com.mdavila_2001.tripadvisorclonemarcelodavila.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mdavila_2001.tripadvisorclonemarcelodavila.utils.SessionManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel(application: Application): AndroidViewModel(application) {
    private val sessionManager = SessionManager(application)

    private val _destinationRoute = MutableStateFlow<String?>(null)
    val destinationRoute: StateFlow<String?> = _destinationRoute

    init {
        checkUserLoginStatus()
    }

    private fun checkUserLoginStatus() {
        viewModelScope.launch {
            delay(3000)

            if (sessionManager.isLoggedIn()) {
                _destinationRoute.value = "trips"
            } else {
                _destinationRoute.value = "login"
            }
        }
    }
}