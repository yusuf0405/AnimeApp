package com.example.animeapp.screen_home.presentation.ui

import androidx.paging.PagingData
import com.example.animeapp.app.base.BaseViewModel
import com.example.animeapp.screen_home.domain.models.Anime
import com.example.animeapp.screen_home.domain.usecase.CreatePagerAnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val createPagerAnimeUseCase: CreatePagerAnimeUseCase,
) : BaseViewModel() {

    val animeSeasonFlow: Flow<PagingData<Anime>> by lazy(LazyThreadSafetyMode.NONE) {
        createPagerAnimeUseCase.execute()
            .flowOn(Dispatchers.IO)

    }

    fun goAnimeInfoFragment(anime: Anime) =
        navigate(HomeFragmentDirections.actionHomeFragmentToAnimeInfoFragment(anime = anime))

    fun goFavoriteFragment() =
        navigate(HomeFragmentDirections.actionHomeFragmentToFavoriteFragment())

    fun goSearchFragment() =
        navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment())

    fun goSettingFragment() =
        navigate(HomeFragmentDirections.actionHomeFragmentToSettingFragment())

}


