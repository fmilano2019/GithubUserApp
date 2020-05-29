package com.example.githubuserapp.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.ListUserAdapter
import com.example.githubuserapp.util.snackbar
import com.example.githubuserapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    companion object {
        const val TOKEN = "token 1f86f3a2e7360e2e52f41f3c9de2c6cfd13c4d9f"
    }

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: ListUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        setUI()
        setupObservers()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
    }

    private fun setUI() {
        pb_main.visibility = View.VISIBLE
        rv_main.setHasFixedSize(true)
        rv_main.layoutManager = LinearLayoutManager(this@MainActivity)
        adapter = ListUserAdapter(arrayListOf())
        rv_main.adapter = adapter
        srl_main.setOnRefreshListener(this@MainActivity)
    }

    private fun setupObservers() {
        mainViewModel.setUsers()
        mainViewModel.getUsers().observe(this@MainActivity, Observer {
            isLoading(false)
            adapter.addUsers(it)
        })
        mainViewModel.getMessage().observe(this@MainActivity, Observer {
            isLoading(false)
            snackbar(coordinatorLayout, it)
        })
    }

    override fun onRefresh() {
        mainViewModel.setUsers()
    }

    fun isLoading(state: Boolean) {
        when(state) {
            true -> {
                pb_main.visibility = View.VISIBLE
                srl_main.isRefreshing = true
            }
            false -> {
                pb_main.visibility = View.GONE
                srl_main.isRefreshing = false
            }
        }
    }

}
