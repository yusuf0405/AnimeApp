package com.example.animeapp.app.network.models.categories


import com.google.gson.annotations.SerializedName

data class Attributes(
    @SerializedName("childCount") var childCount: Int? = null,
    @SerializedName("createdAt") var createdAt: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("nsfw") var nsfw: Boolean? = null,
    @SerializedName("slug") var slug: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("totalMediaCount") var totalMediaCount: Int? = null,
    @SerializedName("updatedAt") var updatedAt: String? = null
)