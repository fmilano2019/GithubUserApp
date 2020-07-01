package com.example.githubuserapp.activity

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.githubuserapp.R

class MainActivity : AppCompatActivity() {

    companion object {

        private const val SCHEME = "content"
        private const val AUTHORITY = "com.example.githubuserapp"
        private const val TABLE_NAME = "users"
        val URI_FAVORITE: Uri = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()

        const val TOKEN = "token 1f86f3a2e7360e2e52f41f3c9de2c6cfd13c4d9f"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}
