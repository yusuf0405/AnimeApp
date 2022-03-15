package com.example.animeapp.app.network.api

import com.example.animeapp.app.network.models.anime.AnimeResponse
import com.example.animeapp.app.network.models.castings.CastingsResponse
import com.example.animeapp.app.network.models.categories.CategoriesResponse
import com.example.animeapp.app.network.models.episodes.EpisodesResponse
import com.example.animeapp.app.network.models.people.PeopleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeApi {

    @GET("anime")
    suspend fun getAllAnime(
        @Query("page[limit]") pageSize: Int,
        @Query("page[offset]") page: Int,
    ): Response<AnimeResponse>

    @GET("anime")
    suspend fun searchAnime(
        @Query("page[limit]") pageSize: Int,
        @Query("page[offset]") page: Int,
        @Query("filter[text]") query: String,
    ): Response<AnimeResponse>


    @GET("anime/{id}/episodes")
    suspend fun getEpisodesAnime(
        @Path("id") id: Int,
        @Query("page[offset]") page: Int,
        @Query("page[limit]") pageSize: Int,
    ): Response<EpisodesResponse>

    @GET("anime/{id}/castings")
    suspend fun getCastingsAnime(
        @Path("id") id: Int,
    ): Response<CastingsResponse>


    @GET("anime/{id}/categories")
    suspend fun getCategoriesAnime(
        @Path("id") id: Int,
    ): Response<CategoriesResponse>


    @GET("castings/{id}/person")
    suspend fun getAnimePersons(
        @Path("id") id: Int,
    ): Response<PeopleResponse>

}