package com.example.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserapp.R
import com.example.githubuserapp.model.Repository
import kotlinx.android.synthetic.main.item_repository.view.*

class ListReposAdapter(private var repos: ArrayList<Repository>) :
    RecyclerView.Adapter<ListReposAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_repository, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return repos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(repos[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(repos: Repository) {
            with(itemView) {
                tv_item_repo_name.text = repos.name
                tv_item_repo_description.text = repos.description
                tv_item_repo_language.text = repos.language
                tv_item_repo_star.text = repos.stars.toString()
                tv_item_repo_fork.text = repos.forks.toString()
            }
        }
    }

    fun addRepos(data: ArrayList<Repository>) {
        repos.clear()
        repos.addAll(data)
        notifyDataSetChanged()
    }

}
