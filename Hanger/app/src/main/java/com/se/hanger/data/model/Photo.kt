package com.se.hanger.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Photo(
    var photoName: String?,
    var photoUriString: String?
) {
    @PrimaryKey(autoGenerate = true)
    var photoId: Int = 0
}
