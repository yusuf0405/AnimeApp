package com.example.animeapp.screen_search.presentation.ui

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animeapp.R
import com.example.animeapp.app.base.BaseBindingFragment
import com.example.animeapp.app.utils.cons.ANIME_KEY
import com.example.animeapp.app.utils.cons.BACK_TYRE_KEY
import com.example.animeapp.app.utils.assistant_nav.AssistantNav
import com.example.animeapp.app.utils.click_listener.AnimeOnClickListener
import com.example.animeapp.app.utils.loader.LoaderStateAdapter
import com.example.animeapp.databinding.FragmentSearchBinding
import com.example.animeapp.screen_home.domain.models.Anime
import com.example.animeapp.screen_home.presentation.adapters.AnimeAdapter
import com.example.animeapp.screen_search.presentation.adapters.NoDataAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import java.util.*

@ExperimentalCoroutinesApi
@DelicateCoroutinesApi
@AndroidEntryPoint
class SearchFragment : BaseBindingFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate),
    AnimeOnClickListener, SearchView.OnQueryTextListener {

    private val viewModel: SearchViewModel by viewModels()

    private val adapter: AnimeAdapter by lazy(LazyThreadSafetyMode.NONE) {
        AnimeAdapter(actionListener = this)
    }
    private val noDataAdapter: NoDataAdapter by lazy(LazyThreadSafetyMode.NONE) {
        NoDataAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerAdapter(status = false)
        requireBinding().searchEditText.setOnQueryTextListener(this)

        lifecycleScope.launch { viewModel.searchFlow.collectLatest(adapter::submitData) }


        requireBinding().backButton.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_homeFragment)
        }

        adapter.addLoadStateListener { state ->
            if (state.refresh is LoadState.Error)
                Toast.makeText(requireContext(),
                    (state.refresh as LoadState.Error).error.message ?: "",
                    Toast.LENGTH_SHORT).show()
        }


    }

    override fun showAnimeInfo(anime: Anime) {
        findNavController().navigate(R.id.action_searchFragment_to_animeInfoFragment,
            bundleOf(ANIME_KEY to anime, BACK_TYRE_KEY to AssistantNav.INFO_TO_SEARCH))
    }

    override fun onQueryTextSubmit(searchText: String?): Boolean {
        if (searchText != null) {
            setRecyclerAdapter(status = true)
            viewModel.startSearch(searchText)
        }
        return false

    }

    override fun onQueryTextChange(newText: String?): Boolean {
        val searchText = newText!!.lowercase(Locale.getDefault())
        if (searchText.isNotEmpty()) {
            setRecyclerAdapter(status = true)
            viewModel.startSearch(searchText)
        } else {
            setRecyclerAdapter(status = false)
        }
        return false
    }


    private fun setRecyclerAdapter(status: Boolean) {
        if (status) {
            requireBinding().searchRecyclerView.layoutManager =
                GridLayoutManager(requireContext(), 3)
            requireBinding().searchRecyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = LoaderStateAdapter(),
                footer = LoaderStateAdapter())
        } else {
            requireBinding().searchRecyclerView.layoutManager =
                LinearLayoutManager(requireContext())
            requireBinding().searchRecyclerView.adapter = noDataAdapter
        }

    }
}