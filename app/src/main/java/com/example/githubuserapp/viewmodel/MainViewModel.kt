package com.example.githubuserapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.model.User

class MainViewModel : ViewModel() {
    private val users = MutableLiveData<ArrayList<User>>()

    fun getUsers(): LiveData<ArrayList<User>> {
        return users
    }

    private fun loadUsers() {

    }
}