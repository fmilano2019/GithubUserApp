package com.example.consumerapp

data class User(

    var id: Int,

    var username: String,

    var avatarUrl: String,

    var name: String?,

    var company: String?,

    var location: String?,

    var repos: Int?,

    var followers: Int?,

    var following: Int?

)
