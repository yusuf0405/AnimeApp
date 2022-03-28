package com.example.animeapp.screen_favorite.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.animeapp.R
import com.example.animeapp.app.utils.click_listener.FavoriteOnClickListener
import com.example.animeapp.app.utils.cons.ID_ANIME_DELETE
import com.example.animeapp.databinding.ItemFavAnimeBinding
import com.example.animeapp.screen_home.domain.models.Anime
import com.squareup.picasso.Picasso
import java.util.*

class FavMoviesDiffCallBack(
    private val oldNotes: List<Anime>,
    private val newNotes: List<Anime>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldNotes.size

    override fun getNewListSize(): Int = newNotes.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldNotes[oldItemPosition]
        val newNote = newNotes[newItemPosition]
        return oldNote.animeId == newNote.animeId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldNotes[oldItemPosition]
        val newNote = newNotes[newItemPosition]
        return oldNote == newNote
    }

}

class FavoriteAdapter(private val actionListener: FavoriteOnClickListener) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    var favoriteAnimeList: MutableList<Anime> = LinkedList()
        set(newValue) {
            val diffCallBack = FavMoviesDiffCallBack(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallBack)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    fun deleteItem(item: Anime) {
        val position = favoriteAnimeList.indexOf(item)
        favoriteAnimeList.remove(item)
        notifyItemRemoved(position)
    }

    inner class FavoriteViewHolder(private val binding: ItemFavAnimeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(anime: Anime) {
            binding.apply {
                moreBtn.tag = anime
                favItemTitle.text =
                    anime.attributes?.canonicalTitle ?: anime.attributes?.titles?.en
                if (anime.attributes?.showType == "TV") animeFavType.text =
                    "${anime.attributes?.showType} Series"
                val length = anime.attributes?.totalLength.toString()
                episodeLengh.text = "Duration: $length minutes"
                val addedDate = anime.getCreatedAt()
                createDate.text = "Added $addedDate"

                Picasso.get()
                    .load(anime.attributes?.posterImage?.small)
                    .resize(200, 200)
                    .into(animeFavImage)
            }

            binding.moreBtn.setOnClickListener {
                showPopupMenu(binding.moreBtn)
            }
            itemView.setOnClickListener {
                actionListener.showAnimeInfo(anime = anime)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val layoutInflater =
            LayoutInflater.from(parent.context).inflate(R.layout.item_fav_anime, parent, false)
        val binding = ItemFavAnimeBinding.bind(layoutInflater)
        return FavoriteViewHolder(binding = binding)
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(view.context, view)
        val context = view.context
        val anime = view.tag as Anime
        popupMenu.menu.add(0, ID_ANIME_DELETE, Menu.NONE, context.getString(R.string.delete))

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                ID_ANIME_DELETE -> actionListener.delete(anime = anime)
            }
            return@setOnMenuItemClickListener true
        }

        popupMenu.show()
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(favoriteAnimeList[position])
    }

    override fun getItemCount(): Int = favoriteAnimeList.size

}

