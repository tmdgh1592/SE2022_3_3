package com.se.hanger.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.se.hanger.data.model.DailyPhoto

@Dao
interface DailyPhotoDao {
    @Insert
    fun insert(dailyPhoto: DailyPhoto)

    @Delete
    fun delete(dailyPhoto: DailyPhoto)

    @Query("SELECT * FROM DailyPhotoTable")
    fun getDailyPhotos()
}
