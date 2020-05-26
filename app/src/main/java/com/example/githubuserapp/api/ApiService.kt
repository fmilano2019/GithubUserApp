package com.example.githubuserapp.api

import com.example.githubuserapp.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {

    @GET("/users")
    fun getUsers(
        @Header("Authorization") auth: String
    ): Call<ArrayList<User>>

}