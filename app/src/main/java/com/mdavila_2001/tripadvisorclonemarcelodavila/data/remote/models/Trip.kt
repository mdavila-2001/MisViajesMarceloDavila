package com.mdavila_2001.tripadvisorclonemarcelodavila.data.remote.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializer

data class Trip (
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("username")
    val username: String,

    @SerializedName("country")
    val country: String,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String
)