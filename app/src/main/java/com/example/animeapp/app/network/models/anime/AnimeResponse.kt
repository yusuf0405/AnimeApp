package com.example.animeapp.app.network.models.anime


import com.google.gson.annotations.SerializedName

data class AnimeResponse(
    @SerializedName("data") var anime: List<AnimeDto>?= null,
)