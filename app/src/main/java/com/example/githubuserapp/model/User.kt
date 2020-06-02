package com.example.githubuserapp.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    var id: Int?,

    @SerializedName("login")
    var username: String?,

    @SerializedName("avatar_url")
    var avatarUrl: String?,

    @SerializedName("name")
    var name: String?,

    @SerializedName("company")
    var company: String?,

    @SerializedName("location")
    var location: String?,

    @SerializedName("public_repos")
    var repos: Int?,

    @SerializedName("followers")
    var followers: Int?,

    @SerializedName("following")
    var following: Int?
)
