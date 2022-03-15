package com.example.animeapp.screen_favorite.data

import com.example.animeapp.app.network.api.CustomApi
import com.example.animeapp.app.network.models.anime.AnimeCustomResponse
import com.example.animeapp.app.utils.BACK4APP_CONTENT_TYPE
import com.example.animeapp.app.utils.toAddAnimeType
import com.example.animeapp.screen_favorite.domain.repository.FavoriteRepository
import com.example.animeapp.screen_home.domain.models.Anime
import retrofit2.Response
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(private val customApi: CustomApi) :
    FavoriteRepository {
    override suspend fun addNewAnime(anime: Anime): Response<Anime> =
        customApi.postAnime(anime = anime.toAddAnimeType(), type = BACK4APP_CONTENT_TYPE)


    override suspend fun deleteAnime(objectId: String): Response<Anime> =
        customApi.deleteAnime(id = objectId, type = BACK4APP_CONTENT_TYPE)

    override suspend fun getAllFavoriteAnime(): Response<AnimeCustomResponse> =
        customApi.getAllFavoriteAnime()

    override suspend fun getAnime(id: String): Response<AnimeCustomResponse> =
        customApi.getFavoriteAnime(id = "{\"animeId\":\"$id\"}")
}