package com.example.animeapp.screen_home.domain.repository

import androidx.paging.PagingData
import com.example.animeapp.app.network.models.anime.AnimeResponse
import com.example.animeapp.screen_home.domain.models.Anime
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface HomeRepository {

    suspend fun getAllAnime(
        pageSize: Int,
        page: Int,
    ): Response<AnimeResponse>

    fun createPagerAnime(): Flow<PagingData<Anime>>

}