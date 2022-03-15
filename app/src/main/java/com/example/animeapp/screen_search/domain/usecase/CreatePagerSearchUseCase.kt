package com.example.animeapp.screen_search.domain.usecase

import androidx.paging.PagingData
import com.example.animeapp.screen_home.domain.models.Anime
import com.example.animeapp.screen_search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreatePagerSearchUseCase @Inject constructor(private val repository: SearchRepository) {
    fun execute(query: String): Flow<PagingData<Anime>> =
        repository.createPagerSearch(query = query)
}