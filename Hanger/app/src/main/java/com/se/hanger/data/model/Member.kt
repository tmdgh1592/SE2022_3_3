package com.se.hanger.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "MemberTable")
data class Member(
    @SerializedName("email") val email: String = "",
    @SerializedName("password") val password: String,
    @SerializedName("username") val username: String,
    @SerializedName("loginStatus") val loginStatus: Boolean = false,
    @SerializedName("loginTrial") val loginTrial: Int = -1,
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}