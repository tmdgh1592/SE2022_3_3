package com.se.hanger.data.db

import androidx.room.*
import com.se.hanger.data.model.DailyPhoto
import com.se.hanger.data.model.Photo
import org.threeten.bp.LocalDate

@Dao
interface DailyPhotoDao {
    @Insert
    suspend fun insert(dailyPhoto: DailyPhoto)

    @Delete
    suspend fun delete(dailyPhoto: DailyPhoto)

    @Query("UPDATE DailyPhotoTable SET photo = :photo WHERE photoDate = :date ")
    suspend fun updateByDate(photo: Photo, date: LocalDate)

//    @Update
//    suspend fun update(dailyPhoto: DailyPhoto)

    @Query("SELECT * FROM DailyPhotoTable")
    suspend fun getDailyPhotos(): List<DailyPhoto>?

    @Query("SELECT * FROM DailyPhotoTable WHERE photoDate = :date")
    suspend fun getDailyPhotoByDate(date: LocalDate): DailyPhoto?
}
