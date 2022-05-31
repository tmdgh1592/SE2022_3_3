package com.se.hanger.data.db.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.se.hanger.data.model.Photo

@ProvidedTypeConverter
class PhotoListTypeConverter(private val gson: Gson) {

    @TypeConverter
    fun listToJson(value: List<Photo>): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<Photo> {
        return gson.fromJson(value, Array<Photo>::class.java).toList()
    }
}