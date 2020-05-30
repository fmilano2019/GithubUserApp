package com.example.githubuserapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubuserapp.activity.MainActivity
import com.example.githubuserapp.api.ApiService
import com.example.githubuserapp.model.User
import com.example.githubuserapp.model.UserQuery
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {
    private val message = MutableLiveData<String>()
    private val users = MutableLiveData<ArrayList<User>>()

    fun getMessage(): LiveData<String> = message
    fun getUsers(): LiveData<ArrayList<User>> = users

    fun setUsers() {
        ApiService.invoke().getUsers(MainActivity.TOKEN).enqueue(object: Callback<ArrayList<User>> {
            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                message.postValue(t.message)
            }

            override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                if (response.isSuccessful) {
                    users.postValue(response.body())
                } else {
                    message.postValue(response.errorBody()?.string())
                }
            }
        })
    }

    fun setQueryUsers(username: String?) {
        ApiService.invoke().queryUser(MainActivity.TOKEN, username).enqueue(object: Callback<UserQuery> {
            override fun onFailure(call: Call<UserQuery>, t: Throwable) {
                message.postValue(t.message)
            }

            override fun onResponse(call: Call<UserQuery>, response: Response<UserQuery>) {
                if (response.isSuccessful) {
                    users.postValue(response.body()?.items)
                } else {
                    if (response.message() != "Unprocessable Entity")
                    message.postValue(response.errorBody()?.string())
                }
            }
        })
    }

}