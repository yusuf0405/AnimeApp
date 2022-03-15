package com.example.animeapp.screen_search.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.animeapp.app.network.api.AnimeApi
import com.example.animeapp.app.network.models.anime.AnimeResponse
import com.example.animeapp.screen_home.domain.models.Anime
import com.example.animeapp.screen_search.data.source.SearchPageSource
import com.example.animeapp.screen_search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val api: AnimeApi) : SearchRepository {
    override suspend fun searchAnime(page: Int, pageSize: Int, query: String): Response<AnimeResponse> =
        api.searchAnime(pageSize = pageSize, page = page, query = query)

    override fun createPagerSearch(query: String): Flow<PagingData<Anime>>  =Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ), pagingSourceFactory ={
            SearchPageSource(this, query = query)
        }

    ).flow
}
