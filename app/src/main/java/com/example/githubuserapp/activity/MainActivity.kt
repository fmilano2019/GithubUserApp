package com.example.githubuserapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.ListUserAdapter
import com.example.githubuserapp.api.ApiHelper
import com.example.githubuserapp.api.RetrofitBuilder
import com.example.githubuserapp.model.User
import com.example.githubuserapp.utils.Status
import com.example.githubuserapp.viewmodel.MainViewModel
import com.example.githubuserapp.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val TOKEN = "token 1f86f3a2e7360e2e52f41f3c9de2c6cfd13c4d9f"
    }

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: ListUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        isLoading(true)
        setupViewModel()
        setUI()
        setupObservers()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(this, ViewModelFactory(ApiHelper(RetrofitBuilder().apiService))).get(MainViewModel::class.java)
    }

    private fun setUI() {
        rv_main.setHasFixedSize(true)
        rv_main.layoutManager = LinearLayoutManager(this)
        adapter = ListUserAdapter(arrayListOf())
        rv_main.adapter = adapter
    }

    private fun setupObservers() {
        mainViewModel.loadUsers().observe(this, Observer {
            it?.let { resource ->
                when(resource.status) {
                    Status.SUCCESS -> {
                        rv_main.visibility = View.VISIBLE
                        isLoading(false)
                        resource.data?.let { users -> retriveList(users)}
                    }
                    Status.LOADING -> {
                        isLoading(true)
                        rv_main.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        rv_main.visibility = View.VISIBLE
                        isLoading(false)
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun retriveList(users: ArrayList<User>) {
        adapter.apply {
            addUsers(users)
            notifyDataSetChanged()
        }
    }

    private fun isLoading(state: Boolean) {
        when(state) {
            true -> pb_main.visibility = View.VISIBLE
            false -> pb_main.visibility = View.GONE
        }
    }
}
