package com.example.githubuserapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.githubuserapp.repository.UserRepository

class DetailViewModel :  ViewModel() {
    private val userRepository = UserRepository()
    private val message = userRepository.getMessage()
    private val user = userRepository.getUser()
    private var repos = userRepository.getRepos()
    private var followers = userRepository.getFollowers()
    private var following = userRepository.getFollowing()

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

}