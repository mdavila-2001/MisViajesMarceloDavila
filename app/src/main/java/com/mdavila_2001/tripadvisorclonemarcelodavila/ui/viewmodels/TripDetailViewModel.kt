package com.mdavila_2001.tripadvisorclonemarcelodavila.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.models.Place
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.network.RetroFitInstance
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.repositories.PlaceRepository
import com.mdavila_2001.tripadvisorclonemarcelodavila.utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    // Evento que indica que un lugar fue eliminado correctamente
    private val _deleteSuccess = MutableSharedFlow<Boolean>(replay = 0)
    val deleteSuccess = _deleteSuccess.asSharedFlow()

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
                    Log.d("DEBUG_IMAGEN", "Datos de imagen recibidos: ${places.firstOrNull()?.imageUrl}")
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

    fun onDeletePlace(place: Place) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }

            try {
                val response = repository.deletePlace(place.id)

                if (response.isSuccessful) {
                    // Emitir evento de éxito para que la UI muestre un Toast
                    _deleteSuccess.emit(true)
                    loadPlaces()
                } else {
                    _uiState.update { it.copy(errorMessage = "Error al eliminar: ${response.message()}") }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = "Error de conexión: ${e.message}") }
            }

        }
    }
}