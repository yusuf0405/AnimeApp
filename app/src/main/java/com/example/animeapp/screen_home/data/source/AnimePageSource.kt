package com.example.animeapp.screen_home.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.animeapp.app.utils.MAX_PAGE_SIZE
import com.example.animeapp.app.utils.toAnime
import com.example.animeapp.screen_home.domain.models.Anime
import com.example.animeapp.screen_home.domain.repository.HomeRepository
import retrofit2.HttpException


class AnimePageSource(
    private val repository: HomeRepository,
) : PagingSource<Int, Anime>() {
    override fun getRefreshKey(state: PagingState<Int, Anime>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Anime> {
        val page: Int = params.key?.plus(20) ?: 1
        val pageSize: Int = params.loadSize.coerceAtMost(MAX_PAGE_SIZE)
        return try {
            val response = repository.getAllAnime(pageSize = pageSize, page = page)
            if (response.isSuccessful) {
                val anime = checkNotNull(response.body()?.anime!!.map { it.toAnime() })
                val nextKey = if (anime.size < pageSize) null else page + 1
                val prevKey = if (page == 1) null else page - 1
                LoadResult.Page(anime, prevKey, nextKey)
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}