package com.se.hanger.data.db.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.se.hanger.data.model.Category

@ProvidedTypeConverter
class CategoryListTypeConverter(private val gson: Gson) {

    @TypeConverter
    fun listToJson(value: List<Category>): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<Category> {
        return gson.fromJson(value, Array<Category>::class.java).toList()
    }
}