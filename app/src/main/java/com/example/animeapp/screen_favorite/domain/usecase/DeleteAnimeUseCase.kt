package com.example.animeapp.screen_favorite.domain.usecase

import com.example.animeapp.screen_favorite.domain.repository.FavoriteRepository
import javax.inject.Inject

class DeleteAnimeUseCase @Inject constructor(private val repository: FavoriteRepository) {
    suspend fun execute(objectId: String) = repository.deleteAnime(objectId = objectId)

}