package com.se.hanger.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.se.hanger.data.model.Cloth

@Dao
interface ClothDao {
    @Insert
    suspend fun insert(cloth: Cloth)

    @Delete
    suspend fun delete(cloth: Cloth)

    @Query("SELECT * FROM ClothTable")
    suspend fun getClothes(): MutableList<Cloth>
}