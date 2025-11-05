package com.mdavila_2001.tripadvisorclonemarcelodavila.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.models.Place
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.repositories.TripRepository

data class TripDetailUiState (
    val isLoading: Boolean = true,
    val places: List<Place> = emptyList(),
    val isMyTrip: Boolean = false,
    val errorMessage: String? = null
)

class TripsPlacesViewModel(
    application: Application,
    private val tripId: Int,
    private val tripOwner: String
): AndroidViewModel(application) {
    private val repository = TripRepository()
}