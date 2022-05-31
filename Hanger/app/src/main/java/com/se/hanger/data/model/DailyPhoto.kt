package com.se.hanger.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "DailyPhotoTable")
data class DailyPhoto(
    var photo: Photo,
    var photoDate: Date
) {
    @PrimaryKey(autoGenerate = true)
    private val id: Int = 0
}