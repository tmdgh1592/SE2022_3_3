package com.se.hanger.data.db.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import org.threeten.bp.LocalDate

@ProvidedTypeConverter
class DateTypeConverter(private val gson: Gson) {

    @TypeConverter
    fun dateToJson(value: LocalDate): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun jsonToDate(value: String): LocalDate {
        return gson.fromJson(value, LocalDate::class.java)
    }
}