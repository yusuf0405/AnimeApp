package com.example.animeapp.app.utils.click_listener

import com.example.animeapp.screen_home.domain.models.Anime

interface AnimeOnClickListener {
    fun showAnimeInfo(anime: Anime)
}

interface FavoriteOnClickListener {

    fun delete(objectId: String)

    fun showAnimeInfo(anime: Anime)
}

