package com.example.githubuserapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.model.User
import com.example.githubuserapp.repository.UserRepository

class DetailViewModel :  ViewModel() {
    private val userRepository = UserRepository()
    private val message = userRepository.getMessage()
    private val user = userRepository.getUser()
    private var repos = userRepository.getRepos()
    private var followers = userRepository.getFollowers()
    private var following = userRepository.getFollowing()
    private var favorites = userRepository.getFavorites()
    private var favorite = userRepository.getFavorite()

    fun getMessage() = message
    fun getUser() = user
    fun getRepos() = repos
    fun getFollowers() = followers
    fun getFollowing() = following

    fun setUser(username: String) {
        userRepository.setUser(username)
    }

    fun setRepos(username: String) {
        userRepository.setRepos(username)
    }

    fun setFollowers(username: String) {
        userRepository.setFollowers(username)
    }

    fun setFollowing(username: String) {
        userRepository.setFollowing(username)
    }

    fun getFavorites() = favorites

    fun getFavorite() = favorite

    fun setFavorites(context: Context) = userRepository.setFavorites(context)

    fun setFavorite(context: Context, id: Int) = userRepository.setFavorite(context, id)

    fun insertFavorite(context: Context, user: User) = userRepository.insertFavorite(context, user)

    fun deleteFavorite(context: Context, user: User) = userRepository.deleteFavorite(context, user)

    fun clearMessage() {
        userRepository.clearMessage()
    }

}