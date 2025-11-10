package com.mdavila_2001.tripadvisorclonemarcelodavila.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.models.dto.PlaceDTO
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.network.RetroFitInstance
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.repositories.PlaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PlaceFormUiState(
    val name: String = "",
    val city: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val timeToSpend: String = "",
    val price: String = "",
    val directions: String = "",

    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class PlaceFormViewModel(
    application: Application,
    private val tripId: Int,
    private val placeId: Int
) : AndroidViewModel(application) {
    private val repository = PlaceRepository(RetroFitInstance.api)

    private val _uiState = MutableStateFlow(PlaceFormUiState())
    val uiState: StateFlow<PlaceFormUiState> = _uiState.asStateFlow()

    private val _navigateBack = MutableStateFlow(false)
    val navigateBack: StateFlow<Boolean> = _navigateBack.asStateFlow()

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    init {
        if (placeId != null) {
            loadPlaceData(placeId)
        }
    }

    private fun loadPlaceData(placeId: Int) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            try {
                val response = repository.getPlacesByTrip(placeId)
                if (response.isSuccessful) {
                    val place = response.body()?.find { it.id == placeId }
                    if (place != null) {
                        _uiState.update {
                            it.copy(
                                name = place.name,
                                city = place.city,
                                description = place.description ?: "",
                                imageUrl = place.imageUrl?.replace("\\/", "/") ?: "", // Limpiamos la URL
                                timeToSpend = place.timeToSpend ?: "",
                                price = place.price ?: "",
                                directions = place.directions ?: ""
                            )
                        }
                    } else {
                        _uiState.update { it.copy(errorMessage = "No se encontró el lugar") }
                    }
                } else {
                    _uiState.update { it.copy(errorMessage = "Error al cargar datos") }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = "Error de conexión") }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun onNameChange(value: String) = _uiState.update { it.copy(name = value) }
    fun onCityChange(value: String) = _uiState.update { it.copy(city = value) }
    fun onDescriptionChange(value: String) = _uiState.update { it.copy(description = value) }
    fun onImageUrlChange(value: String) = _uiState.update { it.copy(imageUrl = value) }
    fun onTimeChange(value: String) = _uiState.update { it.copy(timeToSpend = value) }
    fun onPriceChange(value: String) = _uiState.update { it.copy(price = value) }
    fun onDirectionsChange(value: String) = _uiState.update { it.copy(directions = value) }

    fun onSaveClick() {
        val state = _uiState.value

        if (state.name.isBlank() || state.city.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Nombre y Ciudad son obligatorios") }
            return
        }

        _uiState.update { it.copy(isLoading = true) }

        val placeDto = PlaceDTO(
            name = state.name,
            city = state.city,
            tripId = tripId,
            description = state.description.takeIf { it.isNotBlank() },
            imageUrl = state.imageUrl.takeIf { it.isNotBlank() },
            timeToSpend = state.timeToSpend.takeIf { it.isNotBlank() },
            price = state.price.takeIf { it.isNotBlank() },
            directions = state.directions.takeIf { it.isNotBlank() }
        )

        viewModelScope.launch {
            try {
                val isEditing = (placeId != null)
                val response = if (isEditing) {
                    repository.updatePlace(placeId!!, placeDto)
                } else {
                    repository.createPlace(placeDto)
                }

                if (response.isSuccessful) {
                    _toastMessage.value = if (isEditing) "Lugar actualizado" else "Lugar creado"
                    _navigateBack.value = true
                } else {
                    _uiState.update { it.copy(errorMessage = "Error: ${response.message()}") }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = "Error de conexión: ${e.message}") }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun onToastShown() { _toastMessage.value = null }
    fun onNavigationDone() { _navigateBack.value = false }
}