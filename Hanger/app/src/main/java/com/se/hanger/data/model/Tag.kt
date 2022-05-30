package com.se.hanger.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TagTable")
data class Tag(
    var tagColor: String,
    var tagName: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
