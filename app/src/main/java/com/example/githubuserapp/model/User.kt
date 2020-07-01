package com.example.githubuserapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "users")
data class User(

    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @SerializedName("login")
    @ColumnInfo(name = "username")
    var username: String,

    @SerializedName("avatar_url")
    @ColumnInfo(name = "avatarUrl")
    var avatarUrl: String,

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
) : Parcelable
