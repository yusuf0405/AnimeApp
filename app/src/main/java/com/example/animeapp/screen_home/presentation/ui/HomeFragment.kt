package com.example.animeapp.screen_home.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.animeapp.R
import com.example.animeapp.app.utils.ANIME_KEY
import com.example.animeapp.app.utils.BACK_TYRE_KEY
import com.example.animeapp.app.utils.assistant_nav.AssistantNav
import com.example.animeapp.app.utils.click_listener.AnimeOnClickListener
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

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : Fragment(), AnimeOnClickListener, SwipeRefreshLayout.OnRefreshListener,
    View.OnClickListener {

    private val binding: FragmentHomeBinding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentHomeBinding.inflate(layoutInflater)
    }
    private val viewModel: HomeViewModel by viewModels()

    private val adapter: AnimeAdapter by lazy(LazyThreadSafetyMode.NONE) {
        AnimeAdapter(actionListener = this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTransitions(view = view)
        settingAdapterAttributes()
        onClickListeners()
        observeAnime()
    }


    private fun onClickListeners() {
        binding.swipeRefresh.setOnRefreshListener(this)
        binding.favorite.setOnClickListener(this)
        binding.search.setOnClickListener(this)
    }

    private fun showAllUi() {
        binding.shimmerLayout.stopShimmer()
        binding.shimmerLayout.visibility = View.GONE
        binding.homeFragmentAllUi.visibility = View.VISIBLE
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
        lifecycleScope.launchWhenStarted {
            delay(3000)
            viewModel.animeSeasonFlow.collectLatest(adapter::submitData)
        }

    private fun settingAdapterAttributes() {
        binding.animeRv.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoaderStateAdapter(),
            footer = LoaderStateAdapter()
        )
        adapter.addLoadStateListener { state ->
            if (state.refresh == LoadState.Loading) binding.shimmerLayout.startShimmer()

            if (state.refresh is LoadState.Error)
                Toast.makeText(requireContext(),
                    (state.refresh as LoadState.Error).error.message ?: "",
                    Toast.LENGTH_SHORT).show()
            else showAllUi()
        }
        binding.swipeRefresh.setColorSchemeResources(
            R.color.red,
            R.color.blue,
            R.color.green,
            R.color.red)

    }

    override fun onRefresh() {
        observeAnime()
        binding.swipeRefresh.isRefreshing = true
        binding.swipeRefresh.postDelayed({ binding.swipeRefresh.isRefreshing = false }, 1500)
    }

    override fun onClick(view: View) {
        when (view) {
            binding.favorite -> findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)

            binding.search -> findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }


}