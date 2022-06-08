package com.se.hanger.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.se.hanger.data.model.Cloth
import com.se.hanger.data.model.Tag

@Dao
interface TagDao {
    @Insert
    fun insert(tag: Tag)

    @Delete
    fun delete(tag: Tag)

    @Query("SELECT * FROM TagTable")
    fun getTags(): List<Tag>
}