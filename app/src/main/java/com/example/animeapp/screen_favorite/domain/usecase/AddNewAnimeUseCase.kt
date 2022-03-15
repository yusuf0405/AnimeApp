package com.example.animeapp.screen_favorite.domain.usecase

import com.example.animeapp.screen_favorite.domain.repository.FavoriteRepository
import com.example.animeapp.screen_home.domain.models.Anime
import javax.inject.Inject

class AddNewAnimeUseCase @Inject constructor(private val repository: FavoriteRepository) {
    suspend fun execute(anime: Anime) = repository.addNewAnime(anime = anime)
}