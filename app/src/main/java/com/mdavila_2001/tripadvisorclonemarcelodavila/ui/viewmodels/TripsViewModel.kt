package com.mdavila_2001.tripadvisorclonemarcelodavila.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.models.Trip
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.network.RetroFitInstance
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.repositories.TripRepository
import com.mdavila_2001.tripadvisorclonemarcelodavila.utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class Tab {
    TRIPS,
    MY_TRIPS,
}

data class TripsUiState(
    val isLoading: Boolean = true,
    val selectedTab: Tab = Tab.TRIPS,
    val currentList: List<Trip> = emptyList(),
    val errorMessage: String? = null,
)

class TripsViewModel(application: Application): AndroidViewModel(application) {

    private val repository = TripRepository(RetroFitInstance.api)
    private val sessionManager = SessionManager(application)

    private val _uiState = MutableStateFlow(TripsUiState())
    val uiState: StateFlow<TripsUiState> = _uiState.asStateFlow()

    private val _navigateToLogin = MutableStateFlow(false)
    val navigateToLogin: StateFlow<Boolean> = _navigateToLogin

    private var allTripsCache: List<Trip> = emptyList()
    private var myTripsCache: List<Trip> = emptyList()

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = TripsUiState(isLoading = true)

            val username = sessionManager.getUserName()
            var errorMsg: String? = null

            try {
                val allResponse = repository.getAllTrips()
                if(allResponse.isSuccessful) {
                    allTripsCache = allResponse.body() ?: emptyList()
                } else {
                    throw Exception("Error obteniendo los viajes: ${allResponse.code()} ${allResponse.message()}")
                }
            } catch (e: Exception) {
                errorMsg = "Error de conexión: ${e.message}"
            }

            if (username != null) {
                try {
                    val myResponse = repository.getTripsByUser(username)
                    if (myResponse.isSuccessful) {
                        myTripsCache = myResponse.body() ?: emptyList()
                    } else {
                        errorMsg = "Error al cargar mis viajes"
                    }
                } catch (e: Exception) {
                    errorMsg = "Error de conexión: ${e.message}"
                }
            }

            _uiState.update {
                it.copy(
                    isLoading = false,
                    currentList = allTripsCache,
                    selectedTab = Tab.TRIPS,
                    errorMessage = errorMsg
                )
            }
        }
    }

    fun selectTab(tab: Tab) {
        _uiState.update {
            it.copy(
                selectedTab = tab,
                currentList = if(tab == Tab.TRIPS) {
                    allTripsCache
                } else {
                    myTripsCache
                }
            )
        }
    }

    fun onLogoutClicked() {
        sessionManager.logout()
        _navigateToLogin.value = true
    }
    fun onLoginNavigated() {
        _navigateToLogin.value = false
    }
}