package com.example.animeapp.app.network.models.people


import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("large") var large: String? = null,
    @SerializedName("medium") var medium: String? = null,
    @SerializedName("original") var original: String? = null,
    @SerializedName("small") var small: String? = null,
    @SerializedName("tiny") var tiny: String? = null
)