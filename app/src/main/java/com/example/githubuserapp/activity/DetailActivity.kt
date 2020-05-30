package com.example.githubuserapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.githubuserapp.R
import com.example.githubuserapp.model.User
import com.example.githubuserapp.util.snackbar
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val INTENT_DATA = "intent_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setupUI()
    }

    private fun setupUI() {
        val user = intent.getParcelableExtra<User>(INTENT_DATA)
        snackbar(coordinatorLayout, user?.username)
    }
}
