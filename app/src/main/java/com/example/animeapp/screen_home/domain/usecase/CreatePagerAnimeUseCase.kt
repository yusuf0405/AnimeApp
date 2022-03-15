package com.example.animeapp.screen_home.domain.usecase

import androidx.paging.PagingData
import com.example.animeapp.screen_home.domain.models.Anime
import com.example.animeapp.screen_home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreatePagerAnimeUseCase @Inject constructor(private val repository: HomeRepository) {

    fun execute(): Flow<PagingData<Anime>> = repository.createPagerAnime()
}