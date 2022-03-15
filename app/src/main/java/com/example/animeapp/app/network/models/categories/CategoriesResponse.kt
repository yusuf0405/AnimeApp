package com.example.animeapp.app.network.models.categories


import com.google.gson.annotations.SerializedName

data class CategoriesResponse(
    @SerializedName("data") var сategories: List<Categories>? = null,

)