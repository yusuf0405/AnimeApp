package com.example.animeapp.screen_search.presentation.ui

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animeapp.app.base.BaseFragment
import com.example.animeapp.app.utils.click_listener.AnimeOnClickListener
import com.example.animeapp.app.utils.loader.LoaderStateAdapter
import com.example.animeapp.databinding.FragmentSearchBinding
import com.example.animeapp.screen_home.domain.models.Anime
import com.example.animeapp.screen_home.presentation.adapters.AnimeAdapter
import com.example.animeapp.screen_search.presentation.adapters.NoDataAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

@ExperimentalCoroutinesApi
@DelicateCoroutinesApi
@AndroidEntryPoint
class SearchFragment :
    BaseFragment<FragmentSearchBinding, SearchViewModel>(FragmentSearchBinding::inflate),
    AnimeOnClickListener, SearchView.OnQueryTextListener {

    override val viewModel: SearchViewModel by viewModels()

    override fun onReady(savedInstanceState: Bundle?) {}

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


        requireBinding().backButton.setOnClickListener { viewModel.goBack() }

        adapter.addLoadStateListener { state ->
            if (state.refresh is LoadState.Error)
                Toast.makeText(requireContext(),
                    (state.refresh as LoadState.Error).error.message ?: "",
                    Toast.LENGTH_SHORT).show()
        }


    }

    override fun showAnimeInfo(anime: Anime) {
        viewModel.goAnimeInfoFragment(anime = anime)
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