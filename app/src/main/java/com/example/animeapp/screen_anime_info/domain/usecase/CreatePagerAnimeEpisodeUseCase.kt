package com.example.animeapp.screen_anime_info.domain.usecase

import androidx.paging.PagingData
import com.example.animeapp.app.network.models.episodes.Episodes
import com.example.animeapp.screen_anime_info.domain.repository.AnimeInfoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreatePagerAnimeEpisodeUseCase @Inject constructor(private val repository: AnimeInfoRepository) {
    fun execute(id: Int): Flow<PagingData<Episodes>> = repository.createPagerAnimeEpisode(id = id)
}