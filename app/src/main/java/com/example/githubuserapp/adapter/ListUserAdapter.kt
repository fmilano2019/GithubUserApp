package com.example.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.model.User
import kotlinx.android.synthetic.main.item_user.view.*

class ListUserAdapter(private var listUser: ArrayList<User>) : RecyclerView.Adapter<ListUserAdapter.ViewHolder>() {

    fun setData(data: ArrayList<User>) {
        listUser.clear()
        listUser.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onBindViewHolder(holder: ListUserAdapter.ViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            with(itemView) {
                tv_item_id.text = user.id.toString()
                tv_item_username.text = user.username
                Glide.with(context)
                    .load(user.avatarUrl)
                    .into(iv_item_avatar)
            }
        }
    }

}