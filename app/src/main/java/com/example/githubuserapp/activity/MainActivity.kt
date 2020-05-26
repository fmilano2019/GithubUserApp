package com.example.githubuserapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.ListUserAdapter
import com.example.githubuserapp.api.RetrofitBuilder
import com.example.githubuserapp.model.User
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    companion object {
        const val TOKEN = "token 1f86f3a2e7360e2e52f41f3c9de2c6cfd13c4d9f"
    }

    private var listUser = arrayListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        isLoading(true)
        setUI()
        requestData()
    }

    private fun setUI() {
        rv_main.setHasFixedSize(true)
        rv_main.layoutManager = LinearLayoutManager(this)
    }

    private fun requestData() {
        RetrofitBuilder().getRetrofitService().getUsers(TOKEN).enqueue(object : Callback<ArrayList<User>> {
            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
                isLoading(false)
            }

            override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                rv_main.adapter = response.body()?.let { ListUserAdapter(it) }
                isLoading(false)
            }
        })
    }

    private fun isLoading(state: Boolean) {
        when(state) {
            true -> pb_main.visibility = View.VISIBLE
            false -> pb_main.visibility = View.GONE
        }
    }
}
