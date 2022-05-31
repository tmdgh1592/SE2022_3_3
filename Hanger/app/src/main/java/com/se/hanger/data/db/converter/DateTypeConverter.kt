package com.se.hanger.data.db.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import java.util.*

@ProvidedTypeConverter
class DateTypeConverter(private val gson: Gson) {

    @TypeConverter
    fun dateToJson(value: Date): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun jsonToDate(value: String): Date {
        return gson.fromJson(value, Date::class.java)
    }
}