package com.example.animeapp.screen_favorite.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.animeapp.R
import com.example.animeapp.databinding.ItemNoFavoriteBinding

class NoFavoriteAdapter : RecyclerView.Adapter<NoFavoriteAdapter.ErrorViewHolder>() {

    inner class ErrorViewHolder(binding: ItemNoFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ErrorViewHolder {
        val layoutInflater =
            LayoutInflater.from(parent.context).inflate(R.layout.item_no_favorite, parent, false)
        val binding = ItemNoFavoriteBinding.bind(layoutInflater)
        return ErrorViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: ErrorViewHolder, position: Int) {}

    override fun getItemCount(): Int = 1
}