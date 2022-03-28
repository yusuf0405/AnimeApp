package com.example.animeapp.screen_home.presentation.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.animeapp.R
import com.example.animeapp.app.base.BaseFragment
import com.example.animeapp.app.utils.extensions.hideView
import com.example.animeapp.app.utils.extensions.showView
import com.example.animeapp.app.utils.click_listener.AnimeOnClickListener
import com.example.animeapp.app.utils.loader.LoaderStateAdapter
import com.example.animeapp.databinding.FragmentHomeBinding
import com.example.animeapp.screen_home.domain.models.Anime
import com.example.animeapp.screen_home.presentation.adapters.AnimeAdapter
import com.google.android.material.transition.MaterialFadeThrough
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(FragmentHomeBinding::inflate),
    AnimeOnClickListener, SwipeRefreshLayout.OnRefreshListener,
    View.OnClickListener {

    override val viewModel: HomeViewModel by viewModels()

    override fun onReady(savedInstanceState: Bundle?) {}

    private val adapter: AnimeAdapter by lazy(LazyThreadSafetyMode.NONE) {
        AnimeAdapter(actionListener = this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTransitions(view = view)
        toggleDayNight()
        settingAdapterAttributes()
        onClickListeners()
        observeAnime()
    }

    private fun toggleDayNight() {
        val currentMode = PreferenceManager.getDefaultSharedPreferences(requireContext())
            .getBoolean("theme", false)
        lifecycleScope.launchWhenCreated {
            if (currentMode) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }


    private fun onClickListeners() {
        requireBinding().apply {
            swipeRefresh.setOnRefreshListener(this@HomeFragment)
            favorite.setOnClickListener(this@HomeFragment)
            search.setOnClickListener(this@HomeFragment)
            setting.setOnClickListener(this@HomeFragment)
        }
    }

    override fun onClick(view: View) {
        when (view) {
            requireBinding().favorite -> viewModel.goFavoriteFragment()
            requireBinding().search -> viewModel.goSearchFragment()
            requireBinding().setting -> viewModel.goSettingFragment()
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
        viewModel.goAnimeInfoFragment(anime)

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
}