package com.example.animeapp.screen_anime_info.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.animeapp.app.network.api.AnimeApi
import com.example.animeapp.app.network.models.castings.CastingsResponse
import com.example.animeapp.app.network.models.categories.CategoriesResponse
import com.example.animeapp.app.network.models.episodes.Episodes
import com.example.animeapp.app.network.models.episodes.EpisodesResponse
import com.example.animeapp.app.network.models.people.PeopleResponse
import com.example.animeapp.screen_anime_info.data.source.AnimeEpisodePageSource
import com.example.animeapp.screen_anime_info.domain.repository.AnimeInfoRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class AnimeInfoRepositoryImpl @Inject constructor(private val animeApi: AnimeApi) :
    AnimeInfoRepository {

    override suspend fun getEpisodesAnime(
        id: Int,
        page: Int,
        pageSize: Int,
    ): Response<EpisodesResponse> =
        animeApi.getEpisodesAnime(id = id, pageSize = pageSize, page = page)

    override suspend fun getCastingsAnime(id: Int): Response<CastingsResponse> =
        animeApi.getCastingsAnime(id = id)

    override suspend fun getCategoriesAnime(id: Int): Response<CategoriesResponse> =
        animeApi.getCategoriesAnime(id = id)

    override suspend fun getAnimePeoples(id: Int): Response<PeopleResponse> =
        animeApi.getAnimePersons(id = id)

    override fun createPagerAnimeEpisode(id:Int): Flow<PagingData<Episodes>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory =
        {
            AnimeEpisodePageSource(repository = this, id = id)
        }
    ).flow
}