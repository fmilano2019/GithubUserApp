package com.example.githubuserapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubuserapp.activity.MainActivity
import com.example.githubuserapp.api.ApiService
import com.example.githubuserapp.model.Repository
import com.example.githubuserapp.model.User
import com.example.githubuserapp.model.UserQuery
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {
    private val message = MutableLiveData<String>()
    private val users = MutableLiveData<ArrayList<User>>()
    private val user = MutableLiveData<User>()
    private val repos = MutableLiveData<ArrayList<Repository>>()
    private val followers = MutableLiveData<ArrayList<User>>()
    private val following = MutableLiveData<ArrayList<User>>()

    fun getMessage(): LiveData<String> = message
    fun getUsers(): LiveData<ArrayList<User>> = users
    fun getUser(): LiveData<User> = user
    fun getRepos(): LiveData<ArrayList<Repository>> = repos
    fun getFollowers(): LiveData<ArrayList<User>> = followers
    fun getFollowing(): LiveData<ArrayList<User>> = following


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
                    message.postValue(response.errorBody()?.string())
                }
            }
        })
    }

    fun setUser(username: String) {
        ApiService.invoke().getUser(MainActivity.TOKEN, username).enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                message.postValue(t.message)
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    user.postValue(response.body())
                } else {
                    message.postValue(response.errorBody()?.string())
                }
            }
        })
    }

    fun setRepos(username: String) {
        ApiService.invoke().getRepos(MainActivity.TOKEN, username).enqueue(object : Callback<ArrayList<Repository>> {
            override fun onFailure(call: Call<ArrayList<Repository>>, t: Throwable) {
                message.postValue(t.message)
            }

            override fun onResponse(
                call: Call<ArrayList<Repository>>,
                response: Response<ArrayList<Repository>>
            ) {
                if (response.isSuccessful) {
                    repos.postValue(response.body())
                } else {
                    message.postValue(response.errorBody()?.string())
                }
            }

        })
    }

    fun setFollowers(username: String) {
        ApiService.invoke().getFollowers(MainActivity.TOKEN, username).enqueue(object: Callback<ArrayList<User>> {
            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                message.postValue(t.message)
            }

            override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                if (response.isSuccessful) {
                    followers.postValue(response.body())
                } else {
                    message.postValue(response.errorBody()?.string())
                }
            }
        })
    }

    fun setFollowing(username: String) {
        ApiService.invoke().getFollowing(MainActivity.TOKEN, username).enqueue(object: Callback<ArrayList<User>> {
            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                message.postValue(t.message)
            }

            override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                if (response.isSuccessful) {
                    following.postValue(response.body())
                } else {
                    message.postValue(response.errorBody()?.string())
                }
            }
        })
    }

}