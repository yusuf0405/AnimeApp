package com.example.animeapp.app.network.models.anime


import com.google.gson.annotations.SerializedName

data class AnimeDto(
    @SerializedName("attributes") var attributes: AttributesDto? = null,
    @SerializedName("id") var id: String? = null,
    @SerializedName("objectId") var objectId: String? = null,
    @SerializedName("type") var type: String? = null,
)