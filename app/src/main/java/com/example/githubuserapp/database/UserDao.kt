package com.example.githubuserapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.githubuserapp.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM User")
     fun getUsers(): List<User>

    @Query("SELECT * FROM User WHERE id = :id")
    fun getUserById(id: Int): User

    @Insert
    fun insertUser(user: User)

    @Delete
    fun deleteUser(user: User)

}