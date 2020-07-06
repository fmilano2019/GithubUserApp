package com.example.githubuserapp.utils

import android.database.Cursor
import com.example.githubuserapp.model.User

object MappingHelper {
    fun cursorToArrayList(usersCursor: Cursor?): ArrayList<User> {
        val users = ArrayList<User>()
        usersCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow("id"))
                val username = getString(getColumnIndexOrThrow("username"))
                val avatarUrl = getString(getColumnIndexOrThrow("avatarUrl"))
                users.add(User(id, username, avatarUrl, null, null, null, null, null, null))
            }
        }
        return users
    }
}