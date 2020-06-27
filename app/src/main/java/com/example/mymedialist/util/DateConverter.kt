package com.example.mymedialist.util

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

class DateConverter {

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toDate(dateLong: Long): LocalDate {
        return dateLong.let {
            Instant.ofEpochMilli(dateLong).atZone(ZoneId.systemDefault()).toLocalDate()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromDate(date: LocalDate): Long {
        return date.let {
            date.toEpochDay()
        }
    }


}