package com.example.mymedialist.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.themoviedb.org/3/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface TmdbApiService {
    @GET("search/movie")
    suspend fun searchMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") title: String,
        @Query("page") page: String,
        @Query("include_adult") adult: String
    ): TmdbResponseBody
}

object TmdbApi {
    val retrofitService: TmdbApiService by lazy {
        retrofit.create(TmdbApiService::class.java)
    }
}