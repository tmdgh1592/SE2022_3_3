package com.se.hanger.data.model

import com.google.gson.annotations.SerializedName

data class Member(
    @SerializedName("id") val uid: String,
    @SerializedName("password") var password: String,
    @SerializedName("email") val email: String,
    @SerializedName("username") val username: String,
    @SerializedName("loginStatus") val loginStatus: Boolean,
    @SerializedName("loginTrial") val loginTrial: Int,
)