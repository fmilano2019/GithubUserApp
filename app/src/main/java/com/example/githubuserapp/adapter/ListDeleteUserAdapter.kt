package com.example.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.model.User
import kotlinx.android.synthetic.main.item_user.view.iv_item_delete_avatar
import kotlinx.android.synthetic.main.item_user.view.tv_item_delete_id
import kotlinx.android.synthetic.main.item_user.view.tv_item_delete_username
import kotlinx.android.synthetic.main.item_user_delete.view.*

class ListDeleteUserAdapter(private var users: ArrayList<User>,
                            private val clickListener: (User) -> Unit,
                            private val deleteClickListener: (User) -> Unit
) : RecyclerView.Adapter<ListDeleteUserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDeleteUserAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_delete, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: ListDeleteUserAdapter.ViewHolder, position: Int) {
        holder.bind(users[position], position, clickListener, deleteClickListener)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User, position: Int, clickListener: (User) -> Unit, deleteClickListener: (User) -> Unit) {
            with(itemView) {
                tv_item_delete_id.text = user.id.toString()
                tv_item_delete_username.text = user.username
                ib_item_delete.setOnClickListener {
                    deleteClickListener(user)
                    deleteUser(position)
                }
                setOnClickListener { clickListener.invoke(user) }
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

    fun deleteUser(position: Int) {
        users.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

}