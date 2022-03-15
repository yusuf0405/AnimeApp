package com.example.animeapp.screen_favorite.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.animeapp.R
import com.example.animeapp.app.utils.ANIME_KEY
import com.example.animeapp.app.utils.BACK_TYRE_KEY
import com.example.animeapp.app.utils.assistant_nav.AssistantNav
import com.example.animeapp.app.utils.click_listener.FavoriteOnClickListener
import com.example.animeapp.app.utils.loader.LoadingDialog
import com.example.animeapp.databinding.FragmentFavouriteBinding
import com.example.animeapp.screen_favorite.presentation.FavoriteAdapter
import com.example.animeapp.screen_home.domain.models.Anime
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(), FavoriteOnClickListener {


    private val binding: FragmentFavouriteBinding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentFavouriteBinding.inflate(layoutInflater)
    }
    private val viewModel: FavoriteViewModel by viewModels()

    private val adapter: FavoriteAdapter by lazy(LazyThreadSafetyMode.NONE) {
        FavoriteAdapter(actionListener = this)
    }
    private val loadingDialog: LoadingDialog by lazy(LazyThreadSafetyMode.NONE) {
        LoadingDialog(context = requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = binding.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter
        viewModel.getAllAnime()
        binding.shimmerLayout.startShimmer()


        binding.back.setOnClickListener {
            findNavController().navigate(R.id.action_favoriteFragment_to_homeFragment)
        }

        viewModel.exception.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_favoriteFragment_to_homeFragment)
        }
        viewModel.animeList.observe(viewLifecycleOwner) {
            showUi()
            adapter.favoriteAnimeList = it
        }
        viewModel.message.observe(viewLifecycleOwner) { showToast(message = it) }
        viewModel.loadingDialog.observe(viewLifecycleOwner) { showProgress(status = it) }

    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showProgress(status: Boolean) {
        if (status) loadingDialog.show()
        else loadingDialog.dismiss()
    }

    private fun showUi() {
        binding.shimmerLayout.stopShimmer()
        binding.shimmerLayout.visibility = View.GONE
        binding.favAllUi.visibility = View.VISIBLE
    }

    override fun delete(objectId: String) {
        viewModel.deleteAnimeFavorite(objectId = objectId)
    }

    override fun showAnimeInfo(anime: Anime) {
        findNavController().navigate(R.id.action_favoriteFragment_to_animeInfoFragment,
            bundleOf(ANIME_KEY to anime, BACK_TYRE_KEY to AssistantNav.INFO_TO_FAVORITE))
    }

}
