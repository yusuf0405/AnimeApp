package com.example.animeapp.app.network.models.anime


import com.google.gson.annotations.SerializedName

data class TitlesDto(
    @SerializedName("en") var en: String? = null,
    @SerializedName("en_jp") var enJp: String? = null,
    @SerializedName("en_us") var enUs: String? = null,
    @SerializedName("ja_jp") var jaJp: String? = null,
)