package com.se.hanger.data.db.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.se.hanger.data.model.Tag

@ProvidedTypeConverter
class TagListTypeConverter(private val gson: Gson) {

    @TypeConverter
    fun listToJson(value: List<Tag>): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<Tag> {
        return gson.fromJson(value, Array<Tag>::class.java).toList()
    }
}