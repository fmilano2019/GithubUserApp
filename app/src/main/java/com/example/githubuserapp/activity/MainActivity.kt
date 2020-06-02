package com.example.githubuserapp.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
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

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener,
    androidx.appcompat.widget.SearchView.OnQueryTextListener {

    companion object {
        const val TOKEN = "token 1f86f3a2e7360e2e52f41f3c9de2c6cfd13c4d9f"
    }

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: ListUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        setupUI()
        setupUsersObservers()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
    }

    private fun setupUI() {
        setSupportActionBar(mt_main)
        isLoading(true)
        rv_main.setHasFixedSize(true)
        rv_main.layoutManager = LinearLayoutManager(this@MainActivity)
        adapter = ListUserAdapter(arrayListOf()) {
            toDetailActivity(it)
        }
        rv_main.adapter = adapter
        srl_main.setOnRefreshListener(this@MainActivity)
    }

    private fun setupUsersObservers() {
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

    private fun setupQueryObservers(username: String?) {
        mainViewModel.setQueryUsers(username)
        mainViewModel.getUsers().observe(this, Observer {
            adapter.addUsers(it)
        })
    }

    private fun isLoading(state: Boolean) {
        when(state) {
            true -> {
                pb_main.visibility = View.VISIBLE
            }
            false -> {
                pb_main.visibility = View.GONE
                srl_main.isRefreshing = false
            }
        }
    }

    private fun toDetailActivity(username: String) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.INTENT_DATA, username)
        startActivity(intent)
    }

    override fun onRefresh() {
        mainViewModel.setUsers()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.action_search)?.actionView as androidx.appcompat.widget.SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        pb_main.visibility = View.VISIBLE
        setupQueryObservers(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

}
