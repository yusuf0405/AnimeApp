package com.example.animeapp.screen_anime_info.domain.repository

import androidx.paging.PagingData
import com.example.animeapp.app.network.models.castings.CastingsResponse
import com.example.animeapp.app.network.models.categories.CategoriesResponse
import com.example.animeapp.app.network.models.episodes.Episodes
import com.example.animeapp.app.network.models.episodes.EpisodesResponse
import com.example.animeapp.app.network.models.people.PeopleResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface AnimeInfoRepository {

    suspend fun getEpisodesAnime(id: Int, page: Int, pageSize: Int): Response<EpisodesResponse>

    suspend fun getCastingsAnime(id: Int): Response<CastingsResponse>

    suspend fun getCategoriesAnime(id: Int): Response<CategoriesResponse>

    suspend fun getAnimePeoples(id: Int): Response<PeopleResponse>

    fun createPagerAnimeEpisode(id: Int): Flow<PagingData<Episodes>>

}