package com.se.hanger.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "MemberTable")
data class Member(
    @SerializedName("email") var email: String = "",
    @SerializedName("password") var password: String,
    @SerializedName("username") var username: String,
    @SerializedName("loginStatus") var loginStatus: Boolean = false,
    @SerializedName("loginTrial") var loginTrial: Int = -1,
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}