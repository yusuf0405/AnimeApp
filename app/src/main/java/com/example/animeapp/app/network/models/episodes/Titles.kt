package com.example.animeapp.app.network.models.episodes


import com.google.gson.annotations.SerializedName

data class Titles(
    @SerializedName("en_jp") var enJp: String? = null,
    @SerializedName("en_us") var enUs: String? = null,
    @SerializedName("ja_jp") var jaJp: String? = null
)