package com.example.animeapp.screen_home.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.animeapp.app.network.api.AnimeApi
import com.example.animeapp.app.network.models.anime.AnimeResponse
import com.example.animeapp.screen_home.data.source.AnimePageSource
import com.example.animeapp.screen_home.domain.models.Anime
import com.example.animeapp.screen_home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val animeApi: AnimeApi,
) : HomeRepository {
    override suspend fun getAllAnime(
        pageSize: Int,
        page: Int,
    ): Response<AnimeResponse> = animeApi.getAllAnime(
        pageSize = pageSize,
        page = page,
    )

    override fun createPagerAnime(): Flow<PagingData<Anime>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            AnimePageSource(repository = this)
        }
    ).flow
}