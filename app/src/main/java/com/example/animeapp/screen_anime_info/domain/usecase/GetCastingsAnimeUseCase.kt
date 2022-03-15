package com.example.animeapp.screen_anime_info.domain.usecase

import com.example.animeapp.app.network.models.castings.CastingsResponse
import com.example.animeapp.screen_anime_info.domain.repository.AnimeInfoRepository
import retrofit2.Response
import javax.inject.Inject

class GetCastingsAnimeUseCase @Inject constructor(private val repository: AnimeInfoRepository) {

    suspend fun execute(id: Int): Response<CastingsResponse> = repository.getCastingsAnime(id = id)

}