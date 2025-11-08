package com.mdavila_2001.tripadvisorclonemarcelodavila.ui.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.models.dto.TripDTO
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.network.RetroFitInstance
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.repositories.TripRepository
import com.mdavila_2001.tripadvisorclonemarcelodavila.utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TripFormUiState(
    val name: String = "",
    val country: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class TripFormViewModel(
    application: Application,
    private val tripId: Int?,
    private val initialName: String?,
    private val initialCountry: String?
) : AndroidViewModel(application) {

    private val repository = TripRepository(RetroFitInstance.api)
    private val sessionManager = SessionManager(application)

    private val _uiState = MutableStateFlow(TripFormUiState())
    val uiState: StateFlow<TripFormUiState> = _uiState.asStateFlow()

    private val _navigateBack = MutableStateFlow(false)
    val navigateBack: StateFlow<Boolean> = _navigateBack.asStateFlow()

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    init {
        if (initialName != null && initialCountry != null) {
            _uiState.update {
                it.copy(
                    name = initialName,
                    country = initialCountry
                )
            }
        }
    }

    fun onNameChange(name: String) {
        _uiState.update {
            it.copy(name = name)
        }
    }

    fun onCountryChange(country: String) {
        _uiState.update {
            it.copy(country = country)
        }
    }

    fun onSaveClick() {
        val state = _uiState.value
        val username = sessionManager.getUserName()

        if(state.name.isBlank() || state.country.isBlank()) {
            _uiState.update {
                it.copy(errorMessage = "Por favor complete todos los campos")
            }
            return
        }

        if(username == null) {
            _uiState.update {
                it.copy(errorMessage = "Error de sesión. Por favor inicie sesión nuevamente.")
            }
            return
        }

        _uiState.update {
            it.copy(isLoading = true)
        }

        val tripDto = TripDTO(
            name = state.name,
            country = state.country,
            username = username
        )

        viewModelScope.launch {
            try {
                val isEditing = (tripId != null)

                val response = if (isEditing) {
                    repository.updateTrip(tripId, tripDto)
                } else {
                    repository.createTrip(tripDto)
                }

                if (response.isSuccessful) {
                    val successMessage = if (isEditing) "Viaje actualizado exitosamente" else "Viaje creado exitosamente"
                    _toastMessage.value = successMessage
                    _navigateBack.value = true

                } else {
                    _uiState.update {
                        it.copy(
                            errorMessage = "Error al crear el viaje: ${response.message()}"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        errorMessage = "Error de conexión: ${e.localizedMessage}"
                    )
                }
            } finally {
                _uiState.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }

    fun onNavigationDone() {
        _navigateBack.value = false
    }
}