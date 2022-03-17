package com.example.animeapp.screen_favorite.presentation.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.animeapp.R
import com.example.animeapp.app.base.BaseBindingFragment
import com.example.animeapp.app.hideView
import com.example.animeapp.app.showView
import com.example.animeapp.app.utils.assistant_nav.AssistantNav
import com.example.animeapp.app.utils.click_listener.FavoriteOnClickListener
import com.example.animeapp.app.utils.cons.ANIME_KEY
import com.example.animeapp.app.utils.cons.BACK_TYRE_KEY
import com.example.animeapp.app.utils.loader.LoadingDialog
import com.example.animeapp.app.utils.response.Status
import com.example.animeapp.databinding.FragmentFavouriteBinding
import com.example.animeapp.screen_favorite.presentation.FavoriteAdapter
import com.example.animeapp.screen_favorite.presentation.adapters.NoFavoriteAdapter
import com.example.animeapp.screen_home.domain.models.Anime
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment :
    BaseBindingFragment<FragmentFavouriteBinding>(FragmentFavouriteBinding::inflate),
    FavoriteOnClickListener, View.OnClickListener {

    private val viewModel: FavoriteViewModel by viewModels()

    private val adapter: FavoriteAdapter by lazy(LazyThreadSafetyMode.NONE) {
        FavoriteAdapter(actionListener = this)
    }
    private val noFavAdapter: NoFavoriteAdapter by lazy(LazyThreadSafetyMode.NONE) {
        NoFavoriteAdapter()
    }
    private val loadingDialog: LoadingDialog by lazy(LazyThreadSafetyMode.NONE) {
        LoadingDialog(context = requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireBinding().recyclerView.adapter = adapter
        requireBinding().back.setOnClickListener(this)
        observeRecourses()
        viewModel.getAllAnime()


    }

    private fun observeRecourses() {
        viewModel.message.observe(viewLifecycleOwner) { showToast(message = it) }
        viewModel.loadingDialog.observe(viewLifecycleOwner) { showProgress(status = it) }
        viewModel.recourse.observe(viewLifecycleOwner) { recourse ->
            when (recourse.status) {
                Status.LOADING -> {
                    requireBinding().shimmerLayout.startShimmer()
                }
                Status.SUCCESS -> {
                    showUi()
                    adapter.favoriteAnimeList = recourse.data!!
                    if (recourse.data.isEmpty()) requireBinding().recyclerView.adapter =
                        noFavAdapter
                }
                Status.ERROR -> {
                    findNavController().navigate(R.id.action_favoriteFragment_to_homeFragment)
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
        else loadingDialog.dismiss()
    }

    private fun showUi() {
        requireBinding().apply {
            shimmerLayout.stopShimmer()
            shimmerLayout.hideView()
            favAllUi.showView()
        }
    }

    override fun delete(objectId: String) {
        viewModel.deleteAnimeFavorite(objectId = objectId)
    }

    override fun showAnimeInfo(anime: Anime) {
        findNavController().navigate(R.id.action_favoriteFragment_to_animeInfoFragment,
            bundleOf(ANIME_KEY to anime, BACK_TYRE_KEY to AssistantNav.INFO_TO_FAVORITE))
    }

    override fun onClick(view: View?) {
        when (view) {
            requireBinding().back -> findNavController().navigate(R.id.action_favoriteFragment_to_homeFragment)
        }
    }

}
