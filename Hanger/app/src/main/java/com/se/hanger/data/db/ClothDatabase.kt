package com.se.hanger.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.se.hanger.data.db.converter.CategoryListTypeConverter
import com.se.hanger.data.db.converter.PhotoListTypeConverter
import com.se.hanger.data.db.converter.TagListTypeConverter
import com.se.hanger.data.model.Cloth


@Database(entities = [Cloth::class], version = 7)
@TypeConverters(
    value = [
        PhotoListTypeConverter::class,
        CategoryListTypeConverter::class,
        TagListTypeConverter::class,
    ]
)

abstract class ClothDatabase : RoomDatabase() {
    abstract fun clothDao(): ClothDao

    companion object {
        private var instance: ClothDatabase? = null

        @Synchronized
        fun getInstance(context: Context): ClothDatabase? {
            if (instance == null) {
                val gson = Gson()

                synchronized(ClothDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ClothDatabase::class.java,
                        "cloth-database" //다른 데이터 베이스랑 이름겹치면 꼬임
                    )
                        .addTypeConverter(PhotoListTypeConverter(gson))
                        .addTypeConverter(CategoryListTypeConverter(gson))
                        .addTypeConverter(TagListTypeConverter(gson))
                        .allowMainThreadQueries().build()
                }
            }

            return instance
        }
    }
}