package com.example.consumerapp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    private val userRepository = UserRepository()
    private var users = userRepository.getData()

    fun getData(): LiveData<ArrayList<User>> = users

    fun setData(context: Context) = userRepository.setData(context)

}