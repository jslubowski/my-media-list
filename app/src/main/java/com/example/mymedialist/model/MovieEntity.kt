package com.example.mymedialist.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mymedialist.util.smartTruncate
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "movies")
data class MovieEntity constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val title: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    val description: String,
    @ColumnInfo(name = "release_year")
    val releaseYear: String,
    val rating: Int,
    @ColumnInfo(name = "seen_on_date")
    val seenOnDate: String
) : Parcelable{


    val shortDescription: String
        get() = description.smartTruncate(150)
}


