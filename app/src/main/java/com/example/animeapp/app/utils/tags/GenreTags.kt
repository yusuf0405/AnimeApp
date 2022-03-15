package com.example.animeapp.app.utils.tags

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.animeapp.R
import com.example.animeapp.databinding.TagsGenreBinding

class GenreTags(private val context: Context) {

    private val binding: TagsGenreBinding by lazy(LazyThreadSafetyMode.NONE) {
        TagsGenreBinding.bind(LayoutInflater.from(context).inflate(R.layout.tags_genre, null))
    }

    fun getGenreTag(genreName: String): View {
        binding.genre.text = genreName
        binding.genre.maxLines = 1
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(8, 8, 8, 8)
        binding.genre.layoutParams = layoutParams
        return binding.root
    }

}