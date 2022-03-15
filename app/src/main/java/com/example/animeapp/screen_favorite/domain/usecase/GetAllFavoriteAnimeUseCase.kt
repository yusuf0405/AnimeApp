package com.example.animeapp.screen_favorite.domain.usecase

import com.example.animeapp.app.network.models.anime.AnimeCustomResponse
import com.example.animeapp.screen_favorite.domain.repository.FavoriteRepository
import com.example.animeapp.screen_home.domain.models.Anime
import retrofit2.Response
import javax.inject.Inject

class GetAllFavoriteAnimeUseCase @Inject constructor(private val repository: FavoriteRepository) {
    suspend fun execute(): Response<AnimeCustomResponse> = repository.getAllFavoriteAnime()
}