package com.example.githubuserapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.ListReposAdapter
import com.example.githubuserapp.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_repos.*

/**
 * A simple [Fragment] subclass.
 */
class ReposFragment : Fragment() {

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var adapter: ListReposAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupViewModel()
        setupObservers()
    }

    private fun setupObservers() {
        detailViewModel.getRepos().observe(viewLifecycleOwner, Observer {
            pb_repos.visibility = View.GONE
            adapter.addRepos(it)
        })
        detailViewModel.getMessage().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                pb_repos.visibility = View.GONE
            }
        })
        detailViewModel.clearMessage()
    }

    private fun setupViewModel() {
        detailViewModel =
            ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(
                DetailViewModel::class.java
            )
    }

    private fun setupUI() {
        pb_repos.visibility = View.VISIBLE
        rv_repos.setHasFixedSize(true)
        rv_repos.layoutManager = LinearLayoutManager(context)
        adapter = ListReposAdapter(arrayListOf())
        rv_repos.adapter = adapter
    }

}
