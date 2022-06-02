package com.se.hanger.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.se.hanger.data.db.converter.*
import com.se.hanger.data.model.Cloth
import com.se.hanger.data.model.DailyPhoto
import com.se.hanger.data.model.Member
import com.se.hanger.data.model.Tag


@Database(entities = [Cloth::class, Tag::class, DailyPhoto::class, Member::class], version = 9)
@TypeConverters(
    value = [
        PhotoListTypeConverter::class,
        CategoryListTypeConverter::class,
        TagListTypeConverter::class,
        PhotoTypeConverter::class,
        DateTypeConverter::class
    ]
)

abstract class ClothDatabase : RoomDatabase() {
    abstract fun clothDao(): ClothDao
    abstract fun tagDao(): TagDao
    abstract fun dailyPhotoDao(): DailyPhotoDao
    abstract fun userDao(): MemberDao

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
                        .addTypeConverter(PhotoTypeConverter(gson))
                        .addTypeConverter(DateTypeConverter(gson))
                        .allowMainThreadQueries().build()
                }
            }

            return instance
        }
    }
}