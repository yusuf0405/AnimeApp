package com.example.animeapp.screen_search.domain.repository

import androidx.paging.PagingData
import com.example.animeapp.app.network.models.anime.AnimeResponse
import com.example.animeapp.screen_home.domain.models.Anime
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface SearchRepository {

    suspend fun searchAnime(page: Int, pageSize: Int, query: String): Response<AnimeResponse>

    fun createPagerSearch(query:String): Flow<PagingData<Anime>>

}