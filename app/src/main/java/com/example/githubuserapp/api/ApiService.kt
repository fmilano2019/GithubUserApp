package com.example.githubuserapp.api

import com.example.githubuserapp.model.Repository
import com.example.githubuserapp.model.User
import com.example.githubuserapp.model.UserQuery
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/users")
    fun getUsers(
        @Header("Authorization") auth: String
    ): Call<ArrayList<User>>

    @GET("/search/users")
    fun queryUser(
        @Header("Authorization") auth: String,
        @Query("q") username: String?
    ): Call<UserQuery>

    @GET("/users/{username}")
    fun getUser(
        @Header("Authorization") auth: String,
        @Path("username") username: String
    ): Call<User>

    @GET("/users/{username}/repos")
    fun getRepos(
        @Header("Authorization") auth: String,
        @Path("username") username: String
    ): Call<ArrayList<Repository>>

    @GET("/users/{username}/followers")
    fun getFollowers(
        @Header("Authorization") auth: String,
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("/users/{username}/following")
    fun getFollowing(
        @Header("Authorization") auth: String,
        @Path("username") username: String
    ): Call<ArrayList<User>>

    companion object {
        operator fun invoke(): ApiService {
            return Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }

}