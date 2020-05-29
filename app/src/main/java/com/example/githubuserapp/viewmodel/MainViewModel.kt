package com.example.githubuserapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.activity.MainActivity
import com.example.githubuserapp.api.ApiService
import com.example.githubuserapp.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val users = MutableLiveData<ArrayList<User>>()
    private var message = MutableLiveData<String>()

    fun setUsers() {
        ApiService.invoke().getUsers(MainActivity.TOKEN).enqueue(object: Callback<ArrayList<User>> {
            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                message.postValue(t.message)
            }

            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                if (response.isSuccessful) {
                    users.postValue(response.body())
                } else {
                    message.postValue(response.message())
                }
            }
        })
    }

    fun getUsers(): LiveData<ArrayList<User>> {
        return users
    }

    fun getMessage(): LiveData<String> {
        return message
    }

}
