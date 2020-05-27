package com.example.githubuserapp.api

import com.example.githubuserapp.activity.MainActivity

class ApiHelper(private val apiService: ApiService) {

    suspend fun getUsers() = apiService.getUsers(MainActivity.TOKEN)

}