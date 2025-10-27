package com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.network

import com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.models.Place
import com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.models.Trip
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService{
    @GET("/trips")
    suspend fun getAllTrips(): Response<List<Trip>>

    @GET("/trips/{username}")
    suspend fun getTripsByUser(
        @Path(
            "username"
        ) username: String
    ): Response<List<Trip>>



    @GET("trips/{id}/places")
    suspend fun getPlacesByTrip(
        @Path(
            "id"
        ) id: String
    ): Response<List<Place>>
}
