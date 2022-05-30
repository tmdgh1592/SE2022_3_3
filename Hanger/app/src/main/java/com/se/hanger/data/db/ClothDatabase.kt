package com.se.hanger.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.se.hanger.data.model.Cloth


@Database(entities = [Cloth::class], version = 7)
abstract class ClothDatabase : RoomDatabase() {
    abstract fun clothDao(): ClothDao

    companion object {
        private var instance: ClothDatabase? = null

        @Synchronized
        fun getInstance(context: Context): ClothDatabase? {
            if (instance == null) {
                synchronized(ClothDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ClothDatabase::class.java,
                        "cloth-database" //다른 데이터 베이스랑 이름겹치면 꼬임
                    ).allowMainThreadQueries().build()
                }
            }

            return instance
        }
    }
}