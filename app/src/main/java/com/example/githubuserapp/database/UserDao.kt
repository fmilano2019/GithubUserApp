package com.example.githubuserapp.database

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.githubuserapp.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
     fun getUsers(): List<User>

    @Query("SELECT * FROM users")
    fun getUsersCursor(): Cursor

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUserById(id: Int): User

    @Insert
    fun insertUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("DELETE FROM users WHERE id = :id")
    fun deleteUserById(id: Long): Int

}