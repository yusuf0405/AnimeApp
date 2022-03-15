package com.example.animeapp.screen_anime_info.domain.usecase

import com.example.animeapp.app.network.models.categories.CategoriesResponse
import com.example.animeapp.screen_anime_info.domain.repository.AnimeInfoRepository
import retrofit2.Response
import javax.inject.Inject

class GetCategoriesAnimeUseCase @Inject constructor(private val repository: AnimeInfoRepository) {

    suspend fun execute(id: Int): Response<CategoriesResponse> =
        repository.getCategoriesAnime(id = id)

}