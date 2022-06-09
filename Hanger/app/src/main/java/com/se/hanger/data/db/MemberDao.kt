package com.se.hanger.data.db

import androidx.room.*
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

    @Query("SELECT * FROM MemberTable WHERE uid = :uid")
    suspend fun findById(uid: Int): Member?

    @Query("SELECT * FROM MemberTable WHERE username = :username")
    suspend fun findByUsername(username: String): Member?

    @Update
    suspend fun update(member: Member)
}