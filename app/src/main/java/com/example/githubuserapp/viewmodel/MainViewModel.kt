package com.example.githubuserapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.model.User
import com.example.githubuserapp.repository.UserRepository

class MainViewModel : ViewModel() {
    private var userRepository = UserRepository()
    private var message: LiveData<String> = userRepository.getMessage()
    private val users: LiveData<ArrayList<User>> = userRepository.getUsers()

    fun getMessage(): LiveData<String> = message
    fun getUsers(): LiveData<ArrayList<User>> = users

    fun setUsers() {
        userRepository.setUsers()
    }

    fun setQueryUsers(username: String?) {
        userRepository.setQueryUsers(username)
    }

}
