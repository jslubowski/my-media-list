package com.example.mymedialist.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Result(
    @Json(name = "adult")
    val adult: Boolean,
    @Json(name = "genre_ids")
    val genreIds: List<Int>,
    @Json(name = "id")
    val id: Int,
    @Json(name = "original_language")
    val originalLanguage: String?,
    @Json(name = "original_title")
    val originalTitle: String?,
    @Json(name = "overview")
    val overview: String?,
    @Json(name = "popularity")
    val popularity: Double?,
    @Json(name = "poster_path")
    var posterPath: String? = "null",
    @Json(name = "release_date")
    val releaseDate: String?,
    @Json(name = "title")
    val title: String
) : Parcelable