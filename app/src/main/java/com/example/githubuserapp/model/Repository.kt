package com.example.githubuserapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Repository (
    @SerializedName("name")
    var name: String,

    @SerializedName("description")
    var description: String,

    @SerializedName("language")
    var language: String,

    @SerializedName("stargazers_count")
    var stars: Int,

    @SerializedName("forks")
    var forks: Int
) :  Parcelable