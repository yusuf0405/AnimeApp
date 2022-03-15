package com.example.animeapp.screen_favorite.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
) : ViewModel() {

    private val _animeList: MutableLiveData<List<Anime>> = MutableLiveData()
    val animeList: LiveData<List<Anime>> = _animeList

    private val _loadingDialog: MutableLiveData<Boolean> = MutableLiveData()
    val loadingDialog: LiveData<Boolean> = _loadingDialog

    private val _message: MutableLiveData<String> = MutableLiveData()
    val message: LiveData<String> = _message

    private val _exception: MutableLiveData<Exception> = MutableLiveData()
    val exception: LiveData<Exception> = _exception

    fun getAllAnime() = viewModelScope.launch {
        try {
            val response = withContext(Dispatchers.IO) { getAllFavoriteAnimeUseCase.execute() }
            if (response.isSuccessful) {
                _loadingDialog.value = false
                _animeList.value = response.body()!!.results
            } else {
                _loadingDialog.value = false
                _message.value = "Error loading"
            }
        } catch (e: Exception) {
            _message.value = e.message
            _exception.value = e
        }

    }

    fun deleteAnimeFavorite(objectId: String) = viewModelScope.launch(Dispatchers.Main) {
        _loadingDialog.value = true
        try {
            val response =
                withContext(Dispatchers.IO) { deleteAnimeUseCase.execute(objectId = objectId) }
            if (response.isSuccessful) {
                getAllAnime()
            } else {
                _message.value = response.message()
                _loadingDialog.value = false
            }
        } catch (exception: Exception) {
            _message.value = exception.message
            _exception.value = exception
        }
    }

}