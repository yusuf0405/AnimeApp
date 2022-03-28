package com.example.animeapp.screen_favorite.presentation.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.animeapp.app.base.BaseFragment
import com.example.animeapp.app.utils.extensions.hideView
import com.example.animeapp.app.utils.extensions.showView
import com.example.animeapp.app.utils.click_listener.FavoriteOnClickListener
import com.example.animeapp.app.utils.loader.LoadingDialog
import com.example.animeapp.app.utils.response.Status
import com.example.animeapp.databinding.FragmentFavouriteBinding
import com.example.animeapp.screen_favorite.presentation.FavoriteAdapter
import com.example.animeapp.screen_favorite.presentation.adapters.NoFavoriteAdapter
import com.example.animeapp.screen_home.domain.models.Anime
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment :
    BaseFragment<FragmentFavouriteBinding, FavoriteViewModel>(FragmentFavouriteBinding::inflate),
    FavoriteOnClickListener, View.OnClickListener {


    override val viewModel: FavoriteViewModel by viewModels()

    override fun onReady(savedInstanceState: Bundle?) {}

    private val adapter: FavoriteAdapter by lazy(LazyThreadSafetyMode.NONE) {
        FavoriteAdapter(actionListener = this)
    }
    private val noFavAdapter: NoFavoriteAdapter by lazy(LazyThreadSafetyMode.NONE) {
        NoFavoriteAdapter()
    }
    private val loadingDialog: LoadingDialog by lazy(LazyThreadSafetyMode.NONE) {
        LoadingDialog(context = requireContext())
    }
    private var animeToRemoved: Anime? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireBinding().recyclerView.adapter = adapter
        requireBinding().back.setOnClickListener(this)
        observeRecourses()
        viewModel.getAllAnime()
    }

    private fun observeRecourses() {
        viewModel.message.observe(viewLifecycleOwner) { showToast(message = it) }
        viewModel.loadingDialog.observe(viewLifecycleOwner) {
            showProgress(status = it)
        }
        viewModel.recourse.observe(viewLifecycleOwner) { recourse ->
            when (recourse.status) {
                Status.LOADING -> {
                    requireBinding().shimmerLayout.startShimmer()
                }
                Status.SUCCESS -> {
                    showUi()
                    adapter.favoriteAnimeList = recourse.data!!.toMutableList()
                    if (recourse.data.isEmpty()) requireBinding().recyclerView.adapter =
                        noFavAdapter
                }
                Status.ERROR -> {
                    viewModel.goBack()
                    showToast(message = recourse.message!!)
                }
            }

        }

    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showProgress(status: Boolean) {
        if (status) loadingDialog.show()
        else {
            adapter.deleteItem(item = animeToRemoved!!)
            loadingDialog.dismiss()
        }
    }

    private fun showUi() {
        requireBinding().apply {
            shimmerLayout.stopShimmer()
            shimmerLayout.hideView()
            favAllUi.showView()
        }
    }

    override fun delete(anime: Anime) {
        animeToRemoved = anime
        viewModel.deleteAnimeFavorite(objectId = anime.objectId!!)
    }

    override fun showAnimeInfo(anime: Anime) {
        viewModel.goAnimeInfoFragment(anime = anime)
    }

    override fun onClick(view: View?) {
        when (view) {
            requireBinding().back -> viewModel.goBack()
        }
    }
}
