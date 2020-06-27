package com.example.mymedialist.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.mymedialist.util.DateConverter
import com.example.mymedialist.util.smartTruncate
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate
import java.util.*

@Parcelize
@Entity(tableName = "movies")
@TypeConverters(DateConverter::class)
data class MovieEntity constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val title: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    val description: String,
    @ColumnInfo(name = "release_year")
    val releaseYear: LocalDate,
    val rating: Int,
    @ColumnInfo(name = "seen_on_date")
    val seenOnDate: String
) : Parcelable{

    val shortDescription: String
        get() = description.smartTruncate(150)
}


