package com.example.animeapp.app.utils

import com.example.animeapp.app.network.models.anime.*
import com.example.animeapp.screen_home.domain.models.*

internal fun AnimeDto.toAnime(): Anime =
    Anime(
        attributes = attributes?.toAttributes(),
        animeId = id!!,
        type = type
    )

internal fun Anime.toAddAnimeType(): AnimeAddResponse =
    AnimeAddResponse(
        animeId = animeId,
        attributes = attributes,
        type = type
    )

private fun AttributesDto.toAttributes(): Attributes =
    Attributes(
        abbreviatedTitles = abbreviatedTitles,
        ageRatingGuide = ageRatingGuide,
        averageRating = averageRating,
        canonicalTitle = canonicalTitle,
        ageRating = ageRating,
        coverImage = coverImage?.toCoverImage(),
        coverImageTopOffset = coverImageTopOffset,
        createdAt = createdAt,
        description = description,
        endDate = endDate,
        episodeCount = episodeCount,
        episodeLength = episodeLength,
        favoritesCount = favoritesCount,
        nsfw = nsfw,
        popularityRank = popularityRank,
        posterImage = posterImage?.toPosterImage(),
        ratingRank = ratingRank,
        showType = showType,
        slug = slug,
        startDate = startDate,
        status = status,
        subtype = subtype,
        synopsis = synopsis,
        titles = titles?.toTitles(),
        totalLength = totalLength,
        updatedAt = updatedAt,
        userCount = userCount,
        youtubeVideoId = youtubeVideoId,
    )

private fun CoverImageDto.toCoverImage(): CoverImage =
    CoverImage(
        large = large,
        original = original,
        small = small,
        tiny = tiny
    )

private fun PosterImageDto.toPosterImage(): PosterImage =
    PosterImage(
        large = large,
        medium = medium,
        original = original,
        small = small,
        tiny = tiny,
    )

private fun TitlesDto.toTitles(): Titles =
    Titles(
        en = en,
        enJp = enJp,
        enUs = enUs,
        jaJp = jaJp,
    )