package com.example.animeapp.app.network.models.people


import com.google.gson.annotations.SerializedName

data class People(
    @SerializedName("attributes") var attributes: Attributes? = null,
    @SerializedName("id") var id: String? = null,
    @SerializedName("type") var type: String? = null
)