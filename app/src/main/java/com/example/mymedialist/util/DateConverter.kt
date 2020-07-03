package com.example.mymedialist.util

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalDate

class DateConverter {

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toDate(dateLong: String?): LocalDate {
        return if(dateLong != null && dateLong != "") {
            LocalDate.parse(dateLong)
        } else LocalDate.now()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromDate(date: LocalDate?): String {
        return date?.toString() ?: ""
    }


}