package com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.network

import com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.models.Place
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.models.Trip
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.models.dto.PlaceDTO
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.models.dto.TripDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService{
    @GET("trips")
    suspend fun getAllTrips(): Response<List<Trip>>

    @GET("trips/{username}")
    suspend fun getTripsByUser(
        @Path(
            "username"
        ) username: String
    ): Response<List<Trip>>

    @POST("trips")
    suspend fun createTrip(
        @Body trip: TripDTO
    ): Response<Trip>

    @PUT("trips/{id}")
    suspend fun updateTrip(
        @Path("id") tripId: Int,
        @Body trip: TripDTO
    ): Response<Trip>

    @DELETE("trips/{id}")
    suspend fun deleteTrip(
        @Path("id") tripId: Int
    ): Response<Unit>

    @GET("trips/{id}/places")
    suspend fun getPlacesByTrip(
        @Path(
            "id"
        ) id: Int
    ): Response<List<Place>>

    @POST("places")
    suspend fun createPlace(
        @Body place: PlaceDTO
    ): Response<Place>

    @PUT("places/{placeId}")
    suspend fun updatePlace(
        @Path("placeId") placeId: Int,
        @Body place: PlaceDTO
    ): Response<Place>

    @DELETE("places/{placeId}")
    suspend fun deletePlace(
        @Path("placeId") placeId: Int
    ): Response<Unit>
}
