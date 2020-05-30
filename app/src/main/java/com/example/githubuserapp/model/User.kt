package com.example.githubuserapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("id")
    var id: Int?,

    @SerializedName("login")
    var username: String?,

    @SerializedName("avatar_url")
    var avatarUrl: String?
) : Parcelable