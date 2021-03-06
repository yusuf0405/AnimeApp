package com.example.animeapp.screen_favorite.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.animeapp.app.base.BaseViewModel
import com.example.animeapp.app.utils.response.Resource
import com.example.animeapp.screen_favorite.domain.usecase.DeleteAnimeUseCase
import com.example.animeapp.screen_favorite.domain.usecase.GetAllFavoriteAnimeUseCase
import com.example.animeapp.screen_home.domain.models.Anime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getAllFavoriteAnimeUseCase: GetAllFavoriteAnimeUseCase,
    private val deleteAnimeUseCase: DeleteAnimeUseCase,
) : BaseViewModel() {

    private val _recourse: MutableLiveData<Resource<List<Anime>>> = MutableLiveData()
    val recourse: LiveData<Resource<List<Anime>>> = _recourse

    private val _loadingDialog: MutableLiveData<Boolean> = MutableLiveData()
    val loadingDialog: LiveData<Boolean> = _loadingDialog

    private val _message: MutableLiveData<String> = MutableLiveData()
    val message: LiveData<String> = _message


    fun goBack() = navigateBack()

    fun goAnimeInfoFragment(anime: Anime) =
        navigate(FavoriteFragmentDirections.actionFavoriteFragmentToAnimeInfoFragment(anime = anime))



    fun getAllAnime() = viewModelScope.launch {
        _recourse.value = Resource.loading(data = null)
        try {
            val response = withContext(Dispatchers.IO) { getAllFavoriteAnimeUseCase.execute() }
            if (response.isSuccessful) {
                _recourse.value = Resource.success(data = response.body()!!.results)
            } else {
                _recourse.value = Resource.error(data = null,
                    message = response.message() ?: "Something went wrong")
            }
        } catch (e: Exception) {
            _recourse.value =
                Resource.error(data = null, message = e.message ?: "Something went wrong")

        }

    }

    fun deleteAnimeFavorite(objectId: String) = viewModelScope.launch(Dispatchers.Main) {
        _loadingDialog.value = true
        try {
            val response = withContext(Dispatchers.IO) { deleteAnimeUseCase.execute(objectId) }
            if (response.isSuccessful) {
                _loadingDialog.value = false
            } else {
                _message.value = response.message()
                _loadingDialog.value = false
            }
        } catch (exception: Exception) {
            _message.value = exception.message
        }
    }
}