package com.example.animeapp.screen_search.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.animeapp.R
import com.example.animeapp.databinding.ItemNoDataBinding

class NoDataAdapter : RecyclerView.Adapter<NoDataAdapter.ErrorViewHolder>() {

    inner class ErrorViewHolder(binding: ItemNoDataBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ErrorViewHolder {
        val layoutInflater =
            LayoutInflater.from(parent.context).inflate(R.layout.item_no_data, parent, false)
        val binding = ItemNoDataBinding.bind(layoutInflater)
        return ErrorViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: ErrorViewHolder, position: Int) {}

    override fun getItemCount(): Int = 1
}