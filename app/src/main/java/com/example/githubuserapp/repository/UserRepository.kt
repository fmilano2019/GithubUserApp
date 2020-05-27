package com.example.githubuserapp.repository

import com.example.githubuserapp.api.ApiHelper

class UserRepository(private val apiHelper: ApiHelper) {

    suspend fun getUsers() = apiHelper.getUsers()

}