package com.example.animeapp.screen_home.presentation.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.animeapp.R
import com.example.animeapp.app.base.BaseBindingFragment
import com.example.animeapp.app.hideView
import com.example.animeapp.app.showView
import com.example.animeapp.app.utils.assistant_nav.AssistantNav
import com.example.animeapp.app.utils.click_listener.AnimeOnClickListener
import com.example.animeapp.app.utils.cons.ANIME_KEY
import com.example.animeapp.app.utils.cons.BACK_TYRE_KEY
import com.example.animeapp.app.utils.loader.LoaderStateAdapter
import com.example.animeapp.databinding.FragmentHomeBinding
import com.example.animeapp.screen_home.domain.models.Anime
import com.example.animeapp.screen_home.presentation.adapters.AnimeAdapter
import com.google.android.material.transition.MaterialFadeThrough
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : BaseBindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    AnimeOnClickListener, SwipeRefreshLayout.OnRefreshListener,
    View.OnClickListener {

    private val viewModel: HomeViewModel by viewModels()

    private val adapter: AnimeAdapter by lazy(LazyThreadSafetyMode.NONE) {
        AnimeAdapter(actionListener = this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTransitions(view = view)
        settingAdapterAttributes()
        onClickListeners()
        observeAnime()
    }


    private fun onClickListeners() {
        requireBinding().apply {
            swipeRefresh.setOnRefreshListener(this@HomeFragment)
            favorite.setOnClickListener(this@HomeFragment)
            search.setOnClickListener(this@HomeFragment)
        }

    }

    private fun showAllUi() {
        requireBinding().apply {
            shimmerLayout.stopShimmer()
            shimmerLayout.hideView()
            homeFragmentAllUi.showView()
        }
    }

    private fun setupTransitions(view: View) {
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
        exitTransition = MaterialFadeThrough().apply { duration = 2000 }
        reenterTransition = MaterialFadeThrough().apply { duration = 2000 }
    }

    override fun showAnimeInfo(anime: Anime) =
        findNavController().navigate(R.id.action_homeFragment_to_animeInfoFragment,
            bundleOf(ANIME_KEY to anime, BACK_TYRE_KEY to AssistantNav.INFO_TO_HOME))


    private fun observeAnime() =
        lifecycleScope.launch {
            viewModel.animeSeasonFlow.collectLatest(adapter::submitData)
        }

    private fun settingAdapterAttributes() {
        requireBinding().apply {
            animeRv.itemAnimator = DefaultItemAnimator()
            animeRv.adapter = adapter.withLoadStateHeaderAndFooter(
                header = LoaderStateAdapter(),
                footer = LoaderStateAdapter()
            )
            adapter.addLoadStateListener { state ->
                if (state.refresh == LoadState.Loading) shimmerLayout.startShimmer()
                if (state.refresh != LoadState.Loading) showAllUi()

                if (state.refresh is LoadState.Error)
                    Toast.makeText(requireContext(),
                        (state.refresh as LoadState.Error).error.message ?: "",
                        Toast.LENGTH_SHORT).show()
            }
            swipeRefresh.setColorSchemeResources(
                R.color.red,
                R.color.blue,
                R.color.green,
                R.color.red)
        }


    }

    override fun onRefresh() {
        observeAnime()
        requireBinding().swipeRefresh.isRefreshing = true
        requireBinding().swipeRefresh.postDelayed({
            requireBinding().swipeRefresh.isRefreshing = false
        }, 1500)
    }

    override fun onClick(view: View) {
        when (view) {
            requireBinding().favorite -> findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)
            requireBinding().search -> findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }
}