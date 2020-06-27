package com.example.consumerapp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserRepository {
    private var users = MutableLiveData<ArrayList<User>>()

    fun getData(): LiveData<ArrayList<User>> = users

    fun setData(context: Context) {
        GlobalScope.launch {
            val cursor = context.contentResolver.query(MainActivity.URI_FAVORITE, null, null, null, null)
            users.postValue(MappingHelper.cursorToArrayList(cursor))
        }
    }

}