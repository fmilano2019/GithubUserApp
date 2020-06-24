package com.example.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.model.User
import kotlinx.android.synthetic.main.item_user.view.*

class ListUserAdapter(private var users: ArrayList<User>, private val clickListener: (User) -> Unit)
    : RecyclerView.Adapter<ListUserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: ListUserAdapter.ViewHolder, position: Int) {
        holder.bind(users[position], clickListener)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User, clickListener: (User) -> Unit) {
            with(itemView) {
                tv_item_delete_id.text = user.id.toString()
                tv_item_delete_username.text = user.username
                setOnClickListener { clickListener(user) }
                Glide.with(context)
                    .load(user.avatarUrl)
                    .into(iv_item_delete_avatar)
            }
        }
    }

    fun addUsers(data: ArrayList<User>) {
        users.clear()
        users.addAll(data)
        notifyDataSetChanged()
    }

}