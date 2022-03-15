package com.example.animeapp.app.utils

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AnimeRetrofitClient


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Back4AppRetrofitClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Back4AppRequestInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AnimeRequestInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AnimeOkHttpClient


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Back4AppOkHttpClient


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AnimeHttpLoggingInterceptor


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Back4ttpLoggingInterceptor


