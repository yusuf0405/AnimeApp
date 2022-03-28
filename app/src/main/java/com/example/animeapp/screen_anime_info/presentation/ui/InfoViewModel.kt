package com.example.animeapp.screen_anime_info.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.animeapp.app.base.BaseViewModel
import com.example.animeapp.app.network.models.categories.Categories
import com.example.animeapp.app.network.models.episodes.Episodes
import com.example.animeapp.app.network.models.people.People
import com.example.animeapp.app.utils.response.Resource
import com.example.animeapp.screen_anime_info.domain.usecase.CreatePagerAnimeEpisodeUseCase
import com.example.animeapp.screen_anime_info.domain.usecase.GetAnimePeoplesUseCase
import com.example.animeapp.screen_anime_info.domain.usecase.GetCastingsAnimeUseCase
import com.example.animeapp.screen_anime_info.domain.usecase.GetCategoriesAnimeUseCase
import com.example.animeapp.screen_favorite.domain.usecase.AddNewAnimeUseCase
import com.example.animeapp.screen_favorite.domain.usecase.DeleteAnimeUseCase
import com.example.animeapp.screen_favorite.domain.usecase.GetFavoriteAnimeUseCase
import com.example.animeapp.screen_home.domain.models.Anime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AnimeInfoFragmentViewModel @Inject constructor(
    private val getAnimePeoplesUseCase: GetAnimePeoplesUseCase,
    private val getCastingsAnimeUseCase: GetCastingsAnimeUseCase,
    private val getCategoriesAnimeUseCase: GetCategoriesAnimeUseCase,
    private val addNewAnimeUseCase: AddNewAnimeUseCase,
    private val deleteAnimeUseCase: DeleteAnimeUseCase,
    private val getFavoriteAnimeUseCase: GetFavoriteAnimeUseCase,
    private val createPagerAnimeEpisodeUseCase: CreatePagerAnimeEpisodeUseCase,
) : BaseViewModel() {

    private val _animeId: MutableLiveData<String> = MutableLiveData()
    private val _objectId: MutableLiveData<String> = MutableLiveData()

    private val _animeInfo: MutableLiveData<Resource<List<Categories>>> = MutableLiveData()
    val animeInfo: LiveData<Resource<List<Categories>>> = _animeInfo

    private val _isFavorite: MutableLiveData<Boolean> = MutableLiveData()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private val _setFavImageRecourse: MutableLiveData<Boolean> = MutableLiveData()
    val setFavImageRecourse: LiveData<Boolean> = _setFavImageRecourse

    private val _loadingDialog: MutableLiveData<Boolean> = MutableLiveData()
    val loadingDialog: LiveData<Boolean> = _loadingDialog

    private val _peopleProgressBar: MutableLiveData<Boolean> = MutableLiveData()
    val peopleProgressBar: LiveData<Boolean> = _peopleProgressBar

    private val _message: MutableLiveData<String> = MutableLiveData()
    val message: LiveData<String> = _message

    private val _peopleList: MutableLiveData<List<People>> = MutableLiveData()
    val peopleList: LiveData<List<People>> = _peopleList

    val animeEpisodesFlow: Flow<PagingData<Episodes>> by lazy(LazyThreadSafetyMode.NONE) {
        createPagerAnimeEpisodeUseCase.execute(id = _animeId.value!!.toInt())
            .cachedIn(viewModelScope)
            .flowOn(Dispatchers.IO)
    }

    fun goBack() = navigateBack()

    fun setAnimeId(id: String) { _animeId.value = id }

    fun checkIsFavorite(id: String) = viewModelScope.launch(Dispatchers.Main) {
        _animeInfo.value = Resource.loading(data = null)
        try {
            val response =
                withContext(Dispatchers.IO) { getFavoriteAnimeUseCase.execute(animeId = id) }
            if (response.isSuccessful) {
                if (response.body()!!.results.isNotEmpty()) {
                    _isFavorite.value = true
                    _objectId.value = response.body()!!.results[0].objectId!!
                    allInfoAnime(id = _animeId.value!!.toInt())
                } else {
                    _isFavorite.value = false
                    allInfoAnime(id = _animeId.value!!.toInt())
                }
            }

        } catch (exception: Exception) {
            _animeInfo.value = (Resource.error(data = null,
                message = exception.message ?: "An error has occurred"))
        }
    }

    private fun getPeopleAnime(id: Int) = viewModelScope.launch(Dispatchers.Main) {
        val peopleList = mutableListOf<People>()
        _peopleProgressBar.value = true
        try {
            val result = withContext(Dispatchers.IO) { getCastingsAnimeUseCase.execute(id) }
            if (result.isSuccessful) {
                result.body()?.castings?.forEach {
                    val response =
                        withContext(Dispatchers.IO) { getAnimePeoplesUseCase.execute(id = it.id!!.toInt()) }
                    if (response.isSuccessful) {
                        peopleList.add(response.body()!!.people)
                    } else {
                        _message.value = response.message()
                        _peopleProgressBar.value = false
                    }
                }
            }
        } catch (exception: Exception) {
            _peopleProgressBar.value = false
            _animeInfo.value = (Resource.error(data = null,
                message = exception.message ?: "An error has occurred"))
        }
        _peopleProgressBar.value = false
        _peopleList.value = peopleList
    }

    private fun allInfoAnime(id: Int) = viewModelScope.launch(Dispatchers.Main) {
        try {
            val categoryResponse =
                withContext(Dispatchers.IO) { getCategoriesAnimeUseCase.execute(id) }
            if (categoryResponse.isSuccessful) {
                getPeopleAnime(id = _animeId.value!!.toInt())
                _animeInfo.value = Resource.success(data = categoryResponse.body()?.сategories!!)

            } else _animeInfo.value =
                (Resource.error(data = null,
                    message = categoryResponse.message() ?: "No network connection!"))

        } catch (exception: Exception) {
            _animeInfo.value = (Resource.error(data = null,
                message = exception.message ?: "An error has occurred"))
        }
    }

    fun addAnimeFavorite(anime: Anime) = viewModelScope.launch(Dispatchers.Main) {
        _loadingDialog.value = true
        try {
            val response = withContext(Dispatchers.IO) { addNewAnimeUseCase.execute(anime = anime) }

            if (response.isSuccessful) {
                _setFavImageRecourse.value = false
                _loadingDialog.value = false
                _message.value = "Successfully added"
                _objectId.value = response.body()!!.objectId!!
            } else {
                _message.value = response.message()
                _loadingDialog.value = false
            }
        } catch (exception: Exception) {
            _animeInfo.value = (Resource.error(data = null,
                message = exception.message ?: "An error has occurred"))
        }

    }

    fun deleteAnimeFavorite() = viewModelScope.launch(Dispatchers.Main) {
        _loadingDialog.value = true
        try {
            val response =
                withContext(Dispatchers.IO) { deleteAnimeUseCase.execute(objectId = _objectId.value!!) }
            if (response.isSuccessful) {
                _message.value = "Successfully deleted"
                _setFavImageRecourse.value = true
                _loadingDialog.value = false
            } else {
                _message.value = response.message()
                _loadingDialog.value = false
            }
        } catch (exception: Exception) {
            _animeInfo.value = (Resource.error(data = null,
                message = exception.message ?: "An error has occurred"))
        }
    }
}

