package com.example.animeapp.screen_home.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.PagingData
import com.example.animeapp.screen_home.domain.models.Anime
import com.example.animeapp.screen_home.domain.usecase.CreatePagerAnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val createPagerAnimeUseCase: CreatePagerAnimeUseCase,
) : ViewModel() {

    val animeSeasonFlow: Flow<PagingData<Anime>> by lazy(LazyThreadSafetyMode.NONE) {
        createPagerAnimeUseCase.execute()
    }
    val liveData: LiveData<PagingData<Anime>> = animeSeasonFlow.asLiveData()
}


