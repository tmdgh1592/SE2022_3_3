package com.se.hanger.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "ClothTable")
data class Cloth(
    @SerializedName(value = "buyUrl") var buyUrl: String,
    @SerializedName(value = "clothSize") var clothSize: String,
    @SerializedName(value = "clothName") var clothName: String,
    @SerializedName(value = "clothMemo") var clothMemo: String,
    @SerializedName(value = "clothPhoto") var clothPhoto: Photo,
    @SerializedName(value = "dailyPhoto") var dailyPhoto: List<Photo>,
    @SerializedName(value = "tags") var tags: List<Tag>,
    @SerializedName(value = "categories") var categories: List<Category>
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
