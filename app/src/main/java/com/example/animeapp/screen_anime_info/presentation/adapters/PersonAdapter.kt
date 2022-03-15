package com.example.animeapp.screen_anime_info.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.animeapp.R
import com.example.animeapp.app.network.models.people.People
import com.example.animeapp.databinding.ItemPeopleBinding
import com.squareup.picasso.Picasso


class PostsDiffCallBack(
    private val oldList: List<People>,
    private val newList: List<People>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.id == newItem.id && oldItem.attributes?.name == oldItem.attributes?.name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldList[oldItemPosition]
        val newNote = newList[newItemPosition]
        return oldNote == newNote
    }
}
class PeopleAdapter() :
    RecyclerView.Adapter<PeopleAdapter.PostsViewHolder>() {

    var peopleList: List<People> = emptyList()
        set(newValue) {
            val diffCallBack = PostsDiffCallBack(oldList = field, newList = newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallBack)
            field = newValue
            diffResult.dispatchUpdatesTo(this)

        }

    inner class PostsViewHolder(private val binding: ItemPeopleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(people: People) {
            binding.apply {
                personTitle.text = people.attributes?.name
                Picasso.get()
                    .load(people.attributes?.image?.original ?: people.attributes?.image?.medium)
                    .placeholder(R.drawable.ic_placeholder_no_text)
                    .into(personImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val layoutInflater =
            LayoutInflater.from(parent.context).inflate(R.layout.item_people, parent, false)
        val binding = ItemPeopleBinding.bind(layoutInflater)
        return PostsViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        holder.bind(peopleList[position])
    }

    override fun getItemCount(): Int = peopleList.size

}