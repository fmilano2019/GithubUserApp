package com.example.githubuserapp.model

import com.google.gson.annotations.SerializedName

data class UserQuery (
    @SerializedName("items")
    var items: ArrayList<User>
)