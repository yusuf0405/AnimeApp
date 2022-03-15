package com.example.animeapp.app.network.api

import com.example.animeapp.app.network.models.anime.AnimeAddResponse
import com.example.animeapp.app.network.models.anime.AnimeCustomResponse
import com.example.animeapp.screen_home.domain.models.Anime
import retrofit2.Response
import retrofit2.http.*

interface CustomApi {


    @POST("classes/Anime")
    suspend fun postAnime(
        @Header("Content-Type") type: String,
        @Body anime: AnimeAddResponse,
    ): Response<Anime>

    @DELETE("classes/Anime/{objectId}")
    suspend fun deleteAnime(
        @Header("Content-Type") type: String,
        @Path("objectId") id: String,
    ): Response<Anime>

    @GET("classes/Anime")
    suspend fun getAllFavoriteAnime(): Response<AnimeCustomResponse>

    @GET("classes/Anime")
    suspend fun getFavoriteAnime(
        @Query("where") id: String,
    ): Response<AnimeCustomResponse>


}

