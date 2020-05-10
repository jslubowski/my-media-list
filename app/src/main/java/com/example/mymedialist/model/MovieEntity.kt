package com.example.mymedialist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mymedialist.util.smartTruncate

@Entity(tableName = "movies")
data class MovieEntity constructor(
    @PrimaryKey
    val id: Long,
    val title: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    val description: String,
    @ColumnInfo(name = "release_year")
    val releaseYear: String,
    val rating: Int
) {
    val shortDescription: String
        get() = description.smartTruncate(150)
}


