package com.se.hanger.data.db.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.se.hanger.data.model.Photo

@ProvidedTypeConverter
class PhotoTypeConverter(private val gson: Gson) {

    @TypeConverter
    fun photoToJson(value: Photo): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun jsonToPhoto(value: String): Photo {
        return gson.fromJson(value, Photo::class.java)
    }
}