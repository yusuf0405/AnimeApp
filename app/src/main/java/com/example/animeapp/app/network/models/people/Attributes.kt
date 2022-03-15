package com.example.animeapp.app.network.models.people


import com.google.gson.annotations.SerializedName

data class Attributes(
    @SerializedName("createdAt") var createdAt: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("image") var image: Image? = null,
    @SerializedName("malId") var malId: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("updatedAt") var updatedAt: String? = null,
)