package com.se.hanger.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.se.hanger.data.model.Member

@Dao
interface MemberDao {
    @Insert
    suspend fun insert(user: Member)

    @Delete
    suspend fun delete(user: Member)

    @Query("SELECT * FROM MemberTable WHERE username = :username AND password = :password")
    suspend fun findByUsernameAndPw(username: String, password: String): Member?

    @Query("SELECT * FROM MemberTable")
    suspend fun getUsers(): List<Member>
}