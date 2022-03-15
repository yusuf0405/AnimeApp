package com.example.animeapp.app.network.models.episodes


import Thumbnail
import com.google.gson.annotations.SerializedName

data class Attributes(
    @SerializedName("airdate") var airdate: String? = null,
    @SerializedName("canonicalTitle") var canonicalTitle: String? = null,
    @SerializedName("createdAt") var createdAt: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("length") var length: Int? = null,
    @SerializedName("number") var number: Int? = null,
    @SerializedName("relativeNumber") var relativeNumber: Any? = null,
    @SerializedName("seasonNumber") var seasonNumber: Int? = null,
    @SerializedName("synopsis") var synopsis: String? = null,
    @SerializedName("titles") var titles: Titles? = null,
    @SerializedName("updatedAt") var updatedAt: String? = null,
    @SerializedName("thumbnail") var thumbnail: Thumbnail? = null,
)