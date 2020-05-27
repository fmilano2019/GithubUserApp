package com.example.githubuserapp.api

import com.example.githubuserapp.model.User
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {

    @GET("/users")
    suspend fun getUsers(
        @Header("Authorization") auth: String
    ): ArrayList<User>

}