package com.example.githubuserapp.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.example.githubuserapp.database.UserDatabase
import com.example.githubuserapp.model.User

class FavoriteContentProvider : ContentProvider() {

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return context?.let { UserDatabase.invoke(it).getUserDao().getUsersCursor() }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        when(values) {
            null -> return null
            else -> context?.let { UserDatabase.invoke(it).getUserDao().insertUser(fromContentValues(values)) }
        }
        context?.contentResolver?.notifyChange(uri, null)
        return uri
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        context?.let { UserDatabase.invoke(it).getUserDao().deleteUserById(ContentUris.parseId(uri)) }
        context?.contentResolver?.notifyChange(uri, null)
        return 0
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    private fun fromContentValues(values: ContentValues): User {
        val id = values.getAsInteger("id")
        val username = values.getAsString("username")
        val avatarUrl = values.getAsString("avatarUrl")
        return User(id, username, avatarUrl, null, null, null, null, null, null)
    }

}