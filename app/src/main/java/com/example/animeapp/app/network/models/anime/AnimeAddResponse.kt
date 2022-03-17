package com.example.animeapp.app.network.models.anime

import com.example.animeapp.screen_home.domain.models.Attributes
import java.io.Serializable

data class AnimeAddResponse(
    var animeId: String,
    var attributes: Attributes?,
    var type: String? = null,
) : Serializable
