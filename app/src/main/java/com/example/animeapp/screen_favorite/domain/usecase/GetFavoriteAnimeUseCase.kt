package com.example.animeapp.screen_favorite.domain.usecase

import com.example.animeapp.app.network.models.anime.AnimeCustomResponse
import com.example.animeapp.screen_favorite.domain.repository.FavoriteRepository
import retrofit2.Response
import javax.inject.Inject

class GetFavoriteAnimeUseCase @Inject constructor(private val repository: FavoriteRepository) {
    suspend fun execute(animeId: String): Response<AnimeCustomResponse> =
        repository.getAnime(id = animeId)

}