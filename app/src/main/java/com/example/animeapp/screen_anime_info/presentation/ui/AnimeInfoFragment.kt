package com.example.animeapp.screen_anime_info.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.animeapp.R
import com.example.animeapp.app.base.BaseBindingFragment
import com.example.animeapp.app.hideView
import com.example.animeapp.app.network.models.categories.Categories
import com.example.animeapp.app.showView
import com.example.animeapp.app.utils.assistant_nav.AssistantNav
import com.example.animeapp.app.utils.cons.ANIME_KEY
import com.example.animeapp.app.utils.cons.BACK_TYRE_KEY
import com.example.animeapp.app.utils.loader.LoaderStateAdapter
import com.example.animeapp.app.utils.loader.LoadingDialog
import com.example.animeapp.app.utils.response.Status
import com.example.animeapp.app.utils.tags.GenreTags
import com.example.animeapp.databinding.FragmentInfoAnimeBinding
import com.example.animeapp.screen_anime_info.presentation.PeopleAdapter
import com.example.animeapp.screen_anime_info.presentation.adapters.AnimeEpisodesAdapter
import com.example.animeapp.screen_home.domain.models.Anime
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@DelicateCoroutinesApi
@AndroidEntryPoint
class AnimeInfoFragment :
    BaseBindingFragment<FragmentInfoAnimeBinding>(FragmentInfoAnimeBinding::inflate),
    View.OnClickListener {

    private val viewModel: AnimeInfoFragmentViewModel by viewModels()

    private val anime: Anime by lazy(LazyThreadSafetyMode.NONE) {
        arguments?.getSerializable(ANIME_KEY) as Anime
    }

    private val typeBack: AssistantNav by lazy(LazyThreadSafetyMode.NONE) {
        arguments?.getSerializable(BACK_TYRE_KEY) as AssistantNav
    }

    private val episodesAdapter: AnimeEpisodesAdapter by lazy(LazyThreadSafetyMode.NONE) {
        AnimeEpisodesAdapter()
    }

    private val peopleAdapter: PeopleAdapter by lazy(LazyThreadSafetyMode.NONE) {
        PeopleAdapter()
    }

    private val loadingDialog: LoadingDialog by lazy(LazyThreadSafetyMode.NONE) {
        LoadingDialog(context = requireContext())
    }
    private var isFavorite = false
    private var isTypeAnime = false
    private var episodeVisibility = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingEpisodeAdapterAttributes()
        onClickListeners()
        viewModel.setAnimeId(id = anime.animeId)
        observeRecourses()
        viewModel.checkIsFavorite(id = anime.animeId)
    }

    override fun onClick(view: View?) {
        when (view) {
            requireBinding().episodesButton -> episodeButtonOnClick()
            requireBinding().favourite -> clickFavoriteButton()
        }
    }

    private fun onClickListeners() {
        requireBinding().apply {
            favourite.setOnClickListener(this@AnimeInfoFragment)
            episodesButton.setOnClickListener(this@AnimeInfoFragment)
            toolbar.setNavigationOnClickListener { toolbarNavigation() }
        }

    }

    private fun settingEpisodeAdapterAttributes() {
        requireBinding().episodesRecyclerView.adapter =
            episodesAdapter.withLoadStateHeaderAndFooter(
                header = LoaderStateAdapter(),
                footer = LoaderStateAdapter()
            )

        episodesAdapter.addLoadStateListener { state ->
            if (state.refresh is LoadState.Error)
                Toast.makeText(requireContext(),
                    (state.refresh as LoadState.Error).error.message ?: "",
                    Toast.LENGTH_SHORT).show()
        }
    }

    private fun episodeButtonOnClick() {
        requireBinding().apply {
            if (!episodeVisibility) {
                episodesButton
                episodeVisibility = true
                contentEpisodeRecycler.showView()
            } else {
                episodeVisibility = false
               contentEpisodeRecycler.hideView()
            }
        }

    }

    private fun toolbarNavigation() {
        when (typeBack) {
            AssistantNav.INFO_TO_HOME ->
                findNavController().navigate(R.id.action_animeInfoFragment_to_homeFragment)

            AssistantNav.INFO_TO_FAVORITE ->
                findNavController().navigate(R.id.action_animeInfoFragment_to_favoriteFragment)

            AssistantNav.INFO_TO_SEARCH ->
                findNavController().navigate(R.id.action_animeInfoFragment_to_searchFragment)

        }
    }

    private fun observeRecourses() {
        viewModel.isFavorite.observe(viewLifecycleOwner) { checkingForFavorites(status = it) }
        viewModel.message.observe(viewLifecycleOwner) { showToast(message = it) }
        viewModel.loadingDialog.observe(viewLifecycleOwner) { showProgress(status = it) }
        viewModel.setFavImageRecourse.observe(viewLifecycleOwner) { setFavImageRecourse(status = it) }
        viewModel.peopleProgressBar.observe(viewLifecycleOwner) { showPeopleProgress(status = it) }
        viewModel.peopleList.observe(viewLifecycleOwner) {
            if (isTypeAnime) requireBinding().contentEpisodesButton.isVisible = true
            peopleAdapter.peopleList = it
        }
        lifecycleScope.launch {
            viewModel.animeEpisodesFlow.collectLatest(episodesAdapter::submitData)
        }

        viewModel.animeInfo.observe(viewLifecycleOwner) { resources ->
            when (resources.status) {
                Status.LOADING -> {
                    requireBinding().shimmerLayout.startShimmer()
                }
                Status.SUCCESS -> {
                    bindUi(updateAnime = anime)
                    updateFlowCons(categories = resources.data!!)
                    showUi()
                }
                Status.ERROR -> {
                    showToast(resources.message.toString())
                    toolbarNavigation()
                }
            }
        }
    }

    private fun showToast(message: String) =
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

    private fun showProgress(status: Boolean) {
        if (status) loadingDialog.show()
        else loadingDialog.dismiss()
    }

    private fun showPeopleProgress(status: Boolean) {
        requireBinding().peopleProgressBar.isVisible = status
    }

    private fun setFavImageRecourse(status: Boolean) {
        if (status) {
            isFavorite = false
            requireBinding().favourite.setImageResource(R.drawable.ic_unfavorite)
        } else {
            isFavorite = true
            requireBinding().favourite.setImageResource(R.drawable.ic_favorite)
        }
    }

    private fun clickFavoriteButton() {
        if (isFavorite) viewModel.deleteAnimeFavorite()
        else viewModel.addAnimeFavorite(anime = anime)
    }

    private fun checkingForFavorites(status: Boolean) {
        isFavorite = status
        if (status) requireBinding().favourite.setImageResource(R.drawable.ic_favorite)
        else requireBinding().favourite.setImageResource(R.drawable.ic_unfavorite)

    }

    private fun updateFlowCons(categories: List<Categories>) {
        categories.forEach {
            requireBinding().animeDetailsFlowLayout.addView(
                GenreTags(context = requireContext()).getGenreTag(
                    genreName = it.attributes?.title!!
                )
            )
        }
    }

    private fun showUi() {
        requireBinding().apply {
            shimmerLayout.stopShimmer()
            shimmerLayout.hideView()
            allUiLayout.showView()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun bindUi(updateAnime: Anime) {
        val anime = updateAnime.attributes!!
        requireBinding().animePersonsRecyclerView.adapter = peopleAdapter
        val infoReleased = anime.createdAt?.substring(0, 4)
        requireBinding().apply {
            toolbar.setNavigationIcon(R.drawable.ic_arrow)
            toolbar.title = anime.canonicalTitle ?: anime.titles?.enJp ?: anime.titles?.en
            animeInfoSummary.text = anime.description
            animeDetailsCommunityRating.text = anime.ratingRank.toString()
            animeInfoRelesed.text = infoReleased
            animeDetailsStatus.text = anime.status
            animeDetailsAgeRating.text = anime.ageRating
            animeDetailsAgeRatingGuid.text = anime.ageRatingGuide
            animeDetailsEpisodeCount.text = anime.totalLength.toString()
            animeDetailsEpisodeStartDate.text = anime.startDate
            val rating = anime.averageRating!!.toDouble()
            animeDetailsCommunityRating.text = rating.toString()
            animeDetailsLibraryRatingAdvanced.progress = rating.toInt()

            if (anime.showType == "TV") {
                isTypeAnime = true
                animeDetailsType.text = "${anime.showType} Series"
            } else {
                isTypeAnime = false
                animeDetailsType.text = anime.showType
            }
            Picasso.get()
                .load(anime.posterImage?.original
                    ?: anime.coverImage?.original)
                .into(animeDetailsHeader)
            Picasso.get()
                .load(anime.posterImage?.original
                    ?: anime.coverImage?.original)
                .into(animeDetailsCoverImage)
        }
    }


}