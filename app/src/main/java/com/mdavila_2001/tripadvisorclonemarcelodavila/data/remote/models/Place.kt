package com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.models

import com.google.gson.annotations.SerializedName

data class Place (
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,

    @SerializedName("city")
    val city: String,

    @SerializedName("trip_id")
    val tripId: Int,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("image_url")
    val imageUrl: String? = null,

    @SerializedName("directions")
    val directions: String? = null,

    @SerializedName("time_to_spend")
    val timeToSpend: String? = null,

    @SerializedName("price")
    val price: String? = null,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String
)