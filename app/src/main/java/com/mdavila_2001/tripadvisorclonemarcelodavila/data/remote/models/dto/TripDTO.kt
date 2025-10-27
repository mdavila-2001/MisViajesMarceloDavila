package com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.models.dto

import com.google.gson.annotations.SerializedName

data class TripDTO (
    @SerializedName("name")
    val name: String,

    @SerializedName("username")
    val username: String,

    @SerializedName("country")
    val country: String,
)