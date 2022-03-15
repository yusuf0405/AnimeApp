package com.example.animeapp.screen_anime_info.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.animeapp.R
import com.example.animeapp.app.network.models.episodes.Episodes
import com.example.animeapp.databinding.ItemEpisodesBinding
import com.squareup.picasso.Picasso

class AnimeEpisodesAdapter :
    PagingDataAdapter<Episodes, AnimeEpisodesAdapter.AnimeEpisodesViewHolder>(
        AnimeEpisodesDiffItemCallBack) {

    inner class AnimeEpisodesViewHolder(private val binding: ItemEpisodesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(episodes: Episodes) {
            val info = episodes.attributes!!
            binding.apply {
                episodeItemTitle.text =
                    info.canonicalTitle ?: info.titles?.enUs ?: info.titles?.enJp
                            ?: info.titles?.jaJp
                val season = info.seasonNumber.toString()
                val episode = info.number.toString()
                val duration = info.length.toString()
                episodeItemSeason.text =
                    "Season $season , episode $episode (duration $duration minutes)"
                episodeItemDescription.text = info.description
                Picasso.get()
                    .load(info.thumbnail?.original)
                    .placeholder(R.drawable.anime_place_holder)
                    .into(episodeItemImage)

            }
        }
    }

    override fun onBindViewHolder(holder: AnimeEpisodesViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(episodes = it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeEpisodesViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.item_episodes, parent, false)
        val binding = ItemEpisodesBinding.bind(inflater)
        return AnimeEpisodesViewHolder(binding)
    }
}

private object AnimeEpisodesDiffItemCallBack : DiffUtil.ItemCallback<Episodes>() {

    override fun areItemsTheSame(oldItem: Episodes, newItem: Episodes): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: Episodes, newItem: Episodes): Boolean =
        oldItem.id == newItem.id && oldItem.attributes?.number == newItem.attributes?.number

}