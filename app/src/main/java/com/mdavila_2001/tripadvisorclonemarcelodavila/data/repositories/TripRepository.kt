package com.mdavila_2001.tripadvisorclonemarcelodavila.data.repositories

import com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.models.Trip
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.models.dto.TripDTO
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.network.ApiService
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.network.RetroFitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class TripRepository (
    private val apiService: ApiService = RetroFitInstance.api,
) {
    suspend fun getAllTrips(): Response<List<Trip>> {
        return withContext(Dispatchers.IO) {
            apiService.getAllTrips()
        }
    }

    suspend fun getTripsByUser(username: String): Response<List<Trip>> {
        return withContext(Dispatchers.IO) {
            apiService.getTripsByUser(username)
        }
    }

    suspend fun createTrip(trip: TripDTO): Response<Trip> {
        return withContext(Dispatchers.IO) {
            apiService.createTrip(trip = trip)
        }
    }

    suspend fun updateTrip(tripId: Int, trip: TripDTO): Response<Trip> {
        return withContext(Dispatchers.IO) {
            apiService.updateTrip(tripId = tripId, trip = trip)
        }
    }

    suspend fun deleteTrip(tripId: Int): Response<Unit> {
        return withContext(Dispatchers.IO) {
            apiService.deleteTrip(tripId = tripId)
        }
    }
}