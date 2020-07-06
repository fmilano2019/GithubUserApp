package com.example.githubuserapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.ListDeleteUserAdapter
import com.example.githubuserapp.model.User
import com.example.githubuserapp.utils.SnackbarUtils
import com.example.githubuserapp.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : Fragment(), View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var adapter: ListDeleteUserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupUI()
        setupObserver()
    }

    private fun setupObserver() {
        detailViewModel.setFavorites(requireContext())
        detailViewModel.getFavorites().observe(viewLifecycleOwner, Observer {
            adapter.addUsers(it as ArrayList<User>)
            if (it.isEmpty()) {
                tv_favorite_empty.visibility = View.VISIBLE
            } else {
                tv_favorite_empty.visibility = View.GONE
            }
        })
        detailViewModel.getMessage().observe(viewLifecycleOwner, Observer {
            SnackbarUtils().errorSnackbar(requireContext(), coordinatorLayout, it)
        })
    }

    private fun setupUI() {
        val detail: (User) -> Unit = { toDetailFragment(it) }
        val delete: (User) -> Unit = { deleteUser(it) }
        srl_favorite.setOnRefreshListener(this)
        mt_favorite.setNavigationOnClickListener(this)
        rv_favorite.setHasFixedSize(true)
        rv_favorite.layoutManager = LinearLayoutManager(context)
        adapter = ListDeleteUserAdapter(arrayListOf(), detail, delete)
        rv_favorite.adapter = adapter
    }

    private fun setupViewModel() {
        detailViewModel =
            ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(
                DetailViewModel::class.java
            )
    }

    override fun onClick(v: View?) {
        view?.findNavController()?.navigateUp()
    }

    private fun toDetailFragment(user: User) {
        val actionToDetail = FavoriteFragmentDirections.actionFavoriteFragmentToDetailActivity()
        actionToDetail.id = user.id
        actionToDetail.username = user.username
        actionToDetail.avatarUrl = user.avatarUrl
        view?.findNavController()?.navigate(actionToDetail)
    }

    private fun deleteUser(user: User) {
        detailViewModel.deleteFavorite(requireContext(), user)
    }

    override fun onRefresh() {
        detailViewModel.setFavorites(requireContext()).run {
            srl_favorite.isRefreshing = false
        }
    }

}