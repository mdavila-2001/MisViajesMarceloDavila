package com.mdavila_2001.tripadvisorclonemarcelodavila.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.models.Place
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.network.RetroFitInstance
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.repositories.PlaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PlaceDetailUiState(
    val isLoading: Boolean = true,
    val place: Place? = null,
    val errorMessage: String? = null
)

class PlaceDetailViewModel(
    application: Application,
    private val tripId: Int,
    private val placeId: Int
) : AndroidViewModel(application) {
    private val repository = PlaceRepository(RetroFitInstance.api)

    private val _uiState = MutableStateFlow(PlaceDetailUiState())
    val uiState: StateFlow<PlaceDetailUiState> = _uiState.asStateFlow()

    init {
        loadPLaceDetails()
    }

    private fun loadPLaceDetails() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            var errorMsg: String? = null
            var foundPlace: Place? = null

            try {
                val response = repository.getPlacesByTrip(tripId)

                if(response.isSuccessful) {
                    val placesList = response.body() ?: emptyList()
                    foundPlace = placesList.find { it.id == placeId }

                    if (foundPlace == null) {
                        errorMsg = "Error: No se encontró el lugar con ID $placeId"
                    }
                } else {
                    errorMsg = "Error al cargar lugares: ${response.message()}"
                }
            } catch (e: Exception) {
                errorMsg = "Error de conexión: ${e.message}"
            }

            _uiState.update {
                it.copy(
                    isLoading = false,
                    place = foundPlace,
                    errorMessage = errorMsg
                )
            }
        }
    }
}