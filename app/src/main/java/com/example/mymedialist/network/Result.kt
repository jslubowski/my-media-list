package com.example.mymedialist.network

import androidx.annotation.Nullable
import com.example.mymedialist.util.NullToEmptyString
import com.squareup.moshi.Json

data class Result(
    @Json(name = "adult")
    val adult: Boolean,
    @Json(name = "genre_ids")
    val genreIds: List<Int>,
    @Json(name = "id")
    val id: Int,
    @Json(name = "original_language")
    val originalLanguage: String,
    @Json(name = "original_title")
    val originalTitle: String,
    @Json(name = "overview")
    val overview: String,
    @Json(name = "popularity")
    val popularity: Double,
    @Json(name = "poster_path")
//    @NullToEmptyString
    var posterPath: String? = "null",
    @Json(name = "release_date")
    val releaseDate: String,
    @Json(name = "title")
    val title: String
)