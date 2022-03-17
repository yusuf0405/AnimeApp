package com.example.animeapp.app.di

import com.example.animeapp.app.network.api.AnimeApi
import com.example.animeapp.app.network.api.CustomApi
import com.example.animeapp.screen_anime_info.data.repository.AnimeInfoRepositoryImpl
import com.example.animeapp.screen_anime_info.domain.repository.AnimeInfoRepository
import com.example.animeapp.screen_favorite.data.FavoriteRepositoryImpl
import com.example.animeapp.screen_favorite.domain.repository.FavoriteRepository
import com.example.animeapp.screen_home.data.repository.HomeRepositoryImpl
import com.example.animeapp.screen_home.domain.repository.HomeRepository
import com.example.animeapp.screen_search.data.repository.SearchRepositoryImpl
import com.example.animeapp.screen_search.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideAnimeRepository(animeApi: AnimeApi): HomeRepository =
        HomeRepositoryImpl(animeApi = animeApi)

    @Provides
    @Singleton
    fun provideAnimeInfoRepository(animeApi: AnimeApi): AnimeInfoRepository =
        AnimeInfoRepositoryImpl(animeApi = animeApi)

    @Provides
    @Singleton
    fun provideSearchRepository(animeApi: AnimeApi): SearchRepository =
        SearchRepositoryImpl(api = animeApi)

    @Provides
    @Singleton
    fun provideFavoriteRepository(customApi: CustomApi): FavoriteRepository =
        FavoriteRepositoryImpl(customApi = customApi)


}

