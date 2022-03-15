package com.example.animeapp.app.network.models.anime


import com.google.gson.annotations.SerializedName

data class AttributesDto(
    @SerializedName("abbreviatedTitles") var abbreviatedTitles: List<String>? = null,
    @SerializedName("ageRating") var ageRating: String? = null,
    @SerializedName("ageRatingGuide") var ageRatingGuide: String? = null,
    @SerializedName("averageRating") var averageRating: String? = null,
    @SerializedName("canonicalTitle") var canonicalTitle: String? = null,
    @SerializedName("coverImage") var coverImage: CoverImageDto? = null,
    @SerializedName("coverImageTopOffset") var coverImageTopOffset: Int? = null,
    @SerializedName("createdAt") var createdAt: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("endDate") var endDate: String? = null,
    @SerializedName("episodeCount") var episodeCount: Int? = null,
    @SerializedName("episodeLength") var episodeLength: Int? = null,
    @SerializedName("favoritesCount") var favoritesCount: Int? = null,
    @SerializedName("nsfw") var nsfw: Boolean? = null,
    @SerializedName("popularityRank") var popularityRank: Int? = null,
    @SerializedName("posterImage") var posterImage: PosterImageDto?,
    @SerializedName("ratingRank") var ratingRank: Int? = null,
    @SerializedName("showType") var showType: String? = null,
    @SerializedName("slug") var slug: String? = null,
    @SerializedName("startDate") var startDate: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("subtype") var subtype: String? = null,
    @SerializedName("synopsis") var synopsis: String? = null,
    @SerializedName("titles") var titles: TitlesDto? = null,
    @SerializedName("totalLength") var totalLength: Int? = null,
    @SerializedName("updatedAt") var updatedAt: String? = null,
    @SerializedName("userCount") var userCount: Int? = null,
    @SerializedName("youtubeVideoId") var youtubeVideoId: String? = null,
)