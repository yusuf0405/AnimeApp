package com.example.animeapp.app.di

import com.example.animeapp.app.network.api.AnimeApi
import com.example.animeapp.app.network.api.CustomApi
import com.example.animeapp.app.utils.*
import com.example.animeapp.app.utils.cons.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    @Back4AppRequestInterceptor
    fun back4AppRequestInterceptor() = Interceptor { chain ->
        val request = chain.request()
            .newBuilder()
            .cacheControl(CacheControl.Builder().maxAge(0, TimeUnit.SECONDS).build())
            .addHeader("X-Parse-Application-Id", APPLICATION_ID)
            .addHeader("X-Parse-REST-API-Key", REST_API_KEY)
            .build()
        return@Interceptor chain.proceed(request)
    }

    @Provides
    @Singleton
    @AnimeRequestInterceptor
    fun animeRequestInterceptor() = Interceptor { chain ->
        val request = chain.request()
            .newBuilder()
            .addHeader("Accept", ANIME_CONTENT_TYPE)
            .addHeader("Content-Type", ANIME_CONTENT_TYPE)
            .cacheControl(CacheControl.Builder().maxAge(0, TimeUnit.SECONDS).build())
            .build()
        return@Interceptor chain.proceed(request = request)
    }


    @Provides
    @Singleton
    @Back4AppOkHttpClient
    fun back4AppOkHttpClient(
        @Back4AppRequestInterceptor requestInterceptor: Interceptor,
    ) = OkHttpClient.Builder()
        .addInterceptor(requestInterceptor)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()


    @Provides
    @Singleton
    @AnimeOkHttpClient
    fun animeOkHttpClient(
        @AnimeRequestInterceptor requestInterceptor: Interceptor,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(30, TimeUnit.SECONDS)
            .callTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()


    @Provides
    @Singleton
    @Back4AppRetrofitClient
    fun back4AppRetrofit(@Back4AppOkHttpClient client: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(BACK4APP_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    @AnimeRetrofitClient
    fun animeRetrofit(@AnimeOkHttpClient client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(ANIME_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun postApi(@Back4AppRetrofitClient retrofit: Retrofit): CustomApi =
        retrofit.create(CustomApi::class.java)


    @Provides
    @Singleton
    fun animeApi(@AnimeRetrofitClient retrofit: Retrofit): AnimeApi =
        retrofit.create(AnimeApi::class.java)


}