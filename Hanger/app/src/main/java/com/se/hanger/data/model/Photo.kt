package com.se.hanger.data.model

import android.net.Uri
import androidx.room.PrimaryKey

data class Photo(
    var photoName: String,
    var photoUri: Uri
) {
    @PrimaryKey(autoGenerate = true)
    var photoId: Int = 0
}
