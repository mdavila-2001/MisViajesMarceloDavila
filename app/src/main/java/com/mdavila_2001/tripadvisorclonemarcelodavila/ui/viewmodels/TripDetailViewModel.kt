package com.mdavila_2001.tripadvisorclonemarcelodavila.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.models.Place
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.network.RetroFitInstance
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.repositories.PlaceRepository
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.repositories.TripRepository
import com.mdavila_2001.tripadvisorclonemarcelodavila.utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TripDetailUiState (
    val isLoading: Boolean = true,
    val places: List<Place> = emptyList(),
    val isMyTrip: Boolean = false,
    val errorMessage: String? = null
)

class TripDetailViewModel(
    application: Application,
    private val tripId: Int,
    private val tripOwner: String
): AndroidViewModel(application) {
    private val repository = PlaceRepository(RetroFitInstance.api)
    private val sessionManager = SessionManager(application)

    private val _uiState = MutableStateFlow(TripDetailUiState())
    val uiState: StateFlow<TripDetailUiState> = _uiState.asStateFlow()

    init {
        loadPlaces()
    }

    private fun loadPlaces() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val currentUser = sessionManager.getUserName()
            val isMyTrip = (currentUser == tripOwner)

            var errorMsg: String? = null
            var places: List<Place> = emptyList()

            try {
                val response = repository.getPlacesByTrip(tripId)
                if (response.isSuccessful) {
                    places = response.body() ?: emptyList()
                } else {
                    errorMsg = "Error al cargar lugares: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMsg = "Error al cargar lugares: ${e.message}"
            }

            _uiState.update {
                it.copy(
                    isLoading = false,
                    places = places,
                    isMyTrip = isMyTrip,
                    errorMessage = errorMsg
                )
            }
        }
    }
}