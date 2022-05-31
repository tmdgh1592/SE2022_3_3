package com.se.hanger.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDate

@Entity(tableName = "DailyPhotoTable")
data class DailyPhoto(
    var photo: Photo?,
    var photoDate: LocalDate?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}