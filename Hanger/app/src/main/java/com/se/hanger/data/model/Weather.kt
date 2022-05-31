package com.se.hanger.data.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Weather(
    @SerializedName("response")
    @Expose
    val response: Response
) {
    data class Response(
        @SerializedName("body")
        @Expose
        val body: Body,
        @SerializedName("header")
        @Expose
        val header: Header
    ) {
        data class Body(
            @SerializedName("dataType")
            @Expose
            val dataType: String,
            @SerializedName("items")
            @Expose
            val items: Items,
            @SerializedName("numOfRows")
            @Expose
            val numOfRows: Int,
            @SerializedName("pageNo")
            @Expose
            val pageNo: Int,
            @SerializedName("totalCount")
            @Expose
            val totalCount: Int
        ) {
            data class Items(
                @SerializedName("item")
                @Expose
                val item: List<Item>
            ) {
                data class Item(
                    @SerializedName("baseDate")
                    @Expose
                    val baseDate: String,
                    @SerializedName("baseTime")
                    @Expose
                    val baseTime: String,
                    @SerializedName("category")
                    @Expose
                    val category: String,
                    @SerializedName("nx")
                    @Expose
                    val nx: Int,
                    @SerializedName("ny")
                    @Expose
                    val ny: Int,
                    @SerializedName("obsrValue")
                    @Expose
                    val obsrValue: String
                ): Serializable
            }
        }

        data class Header(
            @SerializedName("resultCode")
            @Expose
            val resultCode: String,
            @SerializedName("resultMsg")
            @Expose
            val resultMsg: String
        )
    }
}