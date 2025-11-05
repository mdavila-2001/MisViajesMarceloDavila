package com.mdavila_2001.tripadvisorclonemarcelodavila.data.repositories

import com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.models.Place
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.models.dto.PlaceDTO
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.network.ApiService
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.network.RetroFitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class PlaceRepository(
    private val apiService: ApiService = RetroFitInstance.api
) {
    suspend fun getPlacesByTrip(tripId: Int): Response<List<Place>> {
        return withContext(Dispatchers.IO) {
            apiService.getPlacesByTrip(tripId)
        }
    }

    suspend fun createPlace(place: PlaceDTO): Response<Place> {
        return withContext(Dispatchers.IO) {
            apiService.createPlace(place)
        }
    }

    suspend fun updatePlace(placeId: Int, place: PlaceDTO): Response<Place> {
        return withContext(Dispatchers.IO) {
            apiService.updatePlace(placeId, place)
        }
    }

    suspend fun deletePlace(placeId: Int): Response<Unit> {
        return withContext(Dispatchers.IO) {
            apiService.deletePlace(placeId)
        }
    }
}