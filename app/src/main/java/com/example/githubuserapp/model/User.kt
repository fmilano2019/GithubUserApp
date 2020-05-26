package com.example.githubuserapp.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    var id: Int?,

    @SerializedName("login")
    var username: String?,

    @SerializedName("avatar_url")
    var avatarUrl: String?
)