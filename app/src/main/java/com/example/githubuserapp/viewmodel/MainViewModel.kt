package com.example.githubuserapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.githubuserapp.repository.UserRepository
import com.example.githubuserapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun loadUsers() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(userRepository.getUsers()))
        } catch (e: Exception) {
            emit(Resource.error(e.localizedMessage ?: "Error", null))
        }
    }

}
