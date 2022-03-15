package com.example.animeapp.screen_favorite.domain.repository

import com.example.animeapp.app.network.models.anime.AnimeCustomResponse
import com.example.animeapp.screen_home.domain.models.Anime
import retrofit2.Response
import retrofit2.http.*

interface FavoriteRepository {

    suspend fun addNewAnime(anime: Anime): Response<Anime>

    suspend fun deleteAnime(objectId: String): Response<Anime>

    suspend fun getAllFavoriteAnime(): Response<AnimeCustomResponse>

    suspend fun getAnime(id: String): Response<AnimeCustomResponse>
}

