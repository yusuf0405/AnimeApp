package com.example.animeapp.app.network.models.anime

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.animeapp.screen_home.domain.models.Attributes
import java.io.Serializable

@Entity(tableName = "anime_favorite")
data class AnimeAddResponse(
    @PrimaryKey var animeId: String,
    var attributes: Attributes?,
    var type: String? = null,
) : Serializable
//I/okhttp.OkHttpClient: {"animeId":"2","attributes":{"abbreviatedTitles":[],"ageRating":"R","ageRatingGuide":"17+ (violence & profanity)","averageRating":"81.89","canonicalTitle":"Cowboy Bebop: Knockin' on Heaven's Door","coverImage":{"large":"https://media.kitsu.io/anime/cover_images/2/large.jpg","original":"https://media.kitsu.io/anime/cover_images/2/original.png","small":"https://media.kitsu.io/anime/cover_images/2/small.jpg","tiny":"https://media.kitsu.io/anime/cover_images/2/tiny.jpg"},"coverImageTopOffset":220,"createdAt":"2013-02-20T16:00:16.085Z","description":"Another day, another bounty—such is the life of the often unlucky crew of the Bebop. However, this routine is interrupted when Faye, who is chasing a fairly worthless target on Mars, witnesses an oil tanker suddenly explode, causing mass hysteria. As casualties mount due to a strange disease spreading through the smoke from the blast, a whopping three hundred million woolong price is placed on the head of the supposed perpetrator.\nWith lives at stake and a solution to their money problems in sight, the Bebop crew springs into action. Spike, Jet, Faye, and Edward, followed closely by Ein, split up to pursue different leads across Alba City. Through their individual investigations, they discover a cover-up scheme involving a pharmaceutical company, revealing a plot that reaches much further than the ragtag team of bounty hunters could have realized.\n[Written by MAL Rewrite]","endDate":"2001-09-01","episodeCount":1,"episodeLength":114,"favoritesCount":308,"nsfw":false,"popularityRank":299,"posterImage":{"large":"https://media.kitsu.io/anime/poster_images/2/large.jpg","medium":"https://media.kitsu.io/anime/poster_images/2/medium.jpg","original":"https://media.kitsu.io/anime/poster_images/2/original.jpg","small":"https://media.kitsu.io/anime/poster_images/2/small.jpg","tiny":"https://media.kitsu.io/anime/poster_images/2/tiny.jpg"},"ratingRank":267,"showType":"movie","slug":"cowboy-bebop-tengoku-no-tobira","startDate":"2001-09-01","status":"finished","subtype":"movie","synopsis":"Another day, another bounty—such is the life of the often unlucky crew of the Bebop. However, this routine is interrupted when Faye, who is chasing a fairly worthless target on Mars, witnesses an oil tanker suddenly explode, causing mass hysteria. As casualties mount due to a strange disease spreading through the smoke from the blast, a whopping three hundred million woolong price is placed on the head of the supposed perpetrator.\nWith lives at stake and a solution to their money problems in sight, the Bebop crew springs into action. Spike, Jet, Faye, and Edward, followed closely by Ein, split up to pursue different leads across Alba City. Through their individual investigations, they discover a cover-up scheme involving a pharmaceutical company, revealing a plot that reaches much further than the ragtag team of bounty hunters could have realized.\n[Written by MAL Rewrite]","titles":{"en":"Cowboy Bebop: The Movie","enJp":"Cowboy Bebop: Tengoku no Tobira","enUs":"Cowboy Bebop: Knockin' on Heaven's Door","jaJp":"カウボーイビバップ天国の扉"},"totalLength":114,"updatedAt":"2022-03-13T18:00:08.244Z","userCount":32308,"youtubeVideoId":"hc7IxJ93jtM"},"type":"anime"}
//I/okhttp.OkHttpClient: {"animeId":"2","attributes":{"abbreviatedTitles":[],"ageRating":"R","ageRatingGuide":"17+ (violence & profanity)","averageRating":"81.89","canonicalTitle":"Cowboy Bebop: Knockin' on Heaven's Door","coverImage":{"large":"https://media.kitsu.io/anime/cover_images/2/large.jpg","original":"https://media.kitsu.io/anime/cover_images/2/original.png","small":"https://media.kitsu.io/anime/cover_images/2/small.jpg","tiny":"https://media.kitsu.io/anime/cover_images/2/tiny.jpg"},"coverImageTopOffset":220,"createdAt":"2013-02-20T16:00:16.085Z","description":"Another day, another bounty—such is the life of the often unlucky crew of the Bebop. However, this routine is interrupted when Faye, who is chasing a fairly worthless target on Mars, witnesses an oil tanker suddenly explode, causing mass hysteria. As casualties mount due to a strange disease spreading through the smoke from the blast, a whopping three hundred million woolong price is placed on the head of the supposed perpetrator.\nWith lives at stake and a solution to their money problems in sight, the Bebop crew springs into action. Spike, Jet, Faye, and Edward, followed closely by Ein, split up to pursue different leads across Alba City. Through their individual investigations, they discover a cover-up scheme involving a pharmaceutical company, revealing a plot that reaches much further than the ragtag team of bounty hunters could have realized.\n[Written by MAL Rewrite]","endDate":"2001-09-01","episodeCount":1,"episodeLength":114,"favoritesCount":308,"nsfw":false,"popularityRank":299,"posterImage":{"large":"https://media.kitsu.io/anime/poster_images/2/large.jpg","medium":"https://media.kitsu.io/anime/poster_images/2/medium.jpg","original":"https://media.kitsu.io/anime/poster_images/2/original.jpg","small":"https://media.kitsu.io/anime/poster_images/2/small.jpg","tiny":"https://media.kitsu.io/anime/poster_images/2/tiny.jpg"},"ratingRank":267,"showType":"movie","slug":"cowboy-bebop-tengoku-no-tobira","startDate":"2001-09-01","status":"finished","subtype":"movie","synopsis":"Another day, another bounty—such is the life of the often unlucky crew of the Bebop. However, this routine is interrupted when Faye, who is chasing a fairly worthless target on Mars, witnesses an oil tanker suddenly explode, causing mass hysteria. As casualties mount due to a strange disease spreading through the smoke from the blast, a whopping three hundred million woolong price is placed on the head of the supposed perpetrator.\nWith lives at stake and a solution to their money problems in sight, the Bebop crew springs into action. Spike, Jet, Faye, and Edward, followed closely by Ein, split up to pursue different leads across Alba City. Through their individual investigations, they discover a cover-up scheme involving a pharmaceutical company, revealing a plot that reaches much further than the ragtag team of bounty hunters could have realized.\n[Written by MAL Rewrite]","titles":{"en":"Cowboy Bebop: The Movie","enJp":"Cowboy Bebop: Tengoku no Tobira","enUs":"Cowboy Bebop: Knockin' on Heaven's Door","jaJp":"カウボーイビバップ天国の扉"},"totalLength":114,"updatedAt":"2022-03-13T18:00:08.244Z","userCount":32308,"youtubeVideoId":"hc7IxJ93jtM"},"createdAt":"2022-03-13T18:35:35.255Z","objectId":"q4wUXChUYO","type":"anime","updatedAt":"2022-03-13T18:35:35.255Z"}
