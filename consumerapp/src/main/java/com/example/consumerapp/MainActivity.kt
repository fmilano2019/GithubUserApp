package com.example.consumerapp

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    companion object {
        private const val SCHEME = "content"
        private const val AUTHORITY = "com.example.githubuserapp"
        private const val TABLE_NAME = "users"
        val URI_FAVORITE: Uri = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()
    }

    private lateinit var usersViewModel: UserViewModel
    private lateinit var adapter: ListUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupViewModel()
        setupObserver()
    }

    private fun setupObserver() {
        usersViewModel.setData(this)
        usersViewModel.getData().observe(this, Observer {
            adapter.addUsers(it)
            if(it.isEmpty()) {
                tv_favorite_empty.visibility = View.VISIBLE
            } else {
                tv_favorite_empty.visibility = View.GONE
            }
        })
    }

    private fun setupViewModel() {
        usersViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)
    }

    private fun setupUI() {
        srl_main.setOnRefreshListener(this)
        rv_main.setHasFixedSize(true)
        rv_main.layoutManager = LinearLayoutManager(this)
        adapter = ListUserAdapter(arrayListOf()) {}
        rv_main.adapter = adapter
    }

    override fun onRefresh() {
        usersViewModel.setData(this).run {
            srl_main.isRefreshing = false
        }
    }

}
