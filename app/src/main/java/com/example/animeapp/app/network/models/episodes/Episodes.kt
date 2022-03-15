package com.example.animeapp.app.network.models.episodes


import com.google.gson.annotations.SerializedName

data class Episodes(
    @SerializedName("attributes") var attributes: Attributes? = null,
    @SerializedName("id") var id: String? = null,
    @SerializedName("type") var type: String? = null
)