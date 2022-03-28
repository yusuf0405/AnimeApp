package com.example.animeapp.screen_search.presentation.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.animeapp.app.base.BaseViewModel
import com.example.animeapp.screen_home.domain.models.Anime
import com.example.animeapp.screen_search.domain.usecase.CreatePagerSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val createPagerAnimeUseCase: CreatePagerSearchUseCase,
) : BaseViewModel() {


    private val _queryBy: MutableLiveData<String> = MutableLiveData<String>()

    init {
        _queryBy.value = ""
    }

    val searchFlow: Flow<PagingData<Anime>> by lazy(LazyThreadSafetyMode.NONE) {
        _queryBy.asFlow()
            .flatMapLatest {
                createPagerAnimeUseCase.execute(query = _queryBy.value!!)
            }
            .cachedIn(viewModelScope)
            .flowOn(Dispatchers.IO)
    }

    fun startSearch(query: String) {
        _queryBy.value = query
    }

    fun goBack() = navigateBack()

    fun goAnimeInfoFragment(anime: Anime) =
        navigate(SearchFragmentDirections.actionSearchFragmentToAnimeInfoFragment(anime = anime))
}