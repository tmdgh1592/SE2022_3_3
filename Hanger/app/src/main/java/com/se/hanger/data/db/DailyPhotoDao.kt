package com.se.hanger.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.se.hanger.data.model.DailyPhoto

@Dao
interface DailyPhotoDao {
    @Insert
    suspend fun insert(dailyPhoto: DailyPhoto)

    @Delete
    suspend fun delete(dailyPhoto: DailyPhoto)

    @Query("SELECT * FROM DailyPhotoTable")
    suspend fun getDailyPhotos(): List<DailyPhoto>
}
