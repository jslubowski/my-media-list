package com.example.mymedialist.util

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalDate

class DateConverter {

    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        @TypeConverter
        @JvmStatic
        fun toDate(dateLong: String?): LocalDate {
            return if (dateLong != null && dateLong != "") {
                LocalDate.parse(dateLong)
            } else LocalDate.now()
        }

        @RequiresApi(Build.VERSION_CODES.O)
        @TypeConverter
        @JvmStatic
        fun fromDate(date: LocalDate?): String {
            return date?.toString() ?: ""
        }

//        @RequiresApi(Build.VERSION_CODES.O)
//        @TypeConverter
//        @JvmStatic
//        fun toDate(dateLong: Long?): LocalDate {
//            return if (dateLong != null) {
//                LocalDate.ofEpochDay(dateLong)
//            } else LocalDate.now()
//        }
//
//        @RequiresApi(Build.VERSION_CODES.O)
//        @TypeConverter
//        @JvmStatic
//        fun fromDate(date: LocalDate?): Long {
//            return date!!.toEpochDay()
//        }
    }
}