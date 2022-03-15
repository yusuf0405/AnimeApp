package com.example.animeapp.app.network.models.people


import com.google.gson.annotations.SerializedName

data class PeopleResponse(
    @SerializedName("data")
    var people: People
)