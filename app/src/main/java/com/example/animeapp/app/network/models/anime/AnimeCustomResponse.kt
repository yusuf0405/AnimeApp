package com.example.animeapp.app.network.models.anime


import com.example.animeapp.screen_home.domain.models.Anime
import com.google.gson.annotations.SerializedName

data class AnimeCustomResponse(
    @SerializedName("results")
    var results: List<Anime>,
)