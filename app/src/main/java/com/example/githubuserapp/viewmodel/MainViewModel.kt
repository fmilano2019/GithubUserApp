package com.example.githubuserapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.model.User
import com.example.githubuserapp.repository.UserRepository

class MainViewModel() : ViewModel() {
    private var userRepository = UserRepository()
    private val users: LiveData<ArrayList<User>> = userRepository.getUsers()
    private var message: LiveData<String> = userRepository.getMessage()

    fun setUsers() {
        userRepository.setUsers()
    }

    fun getUsers(): LiveData<ArrayList<User>> {
        return users
    }

    fun getMessage(): LiveData<String> {
        return message
    }

}
