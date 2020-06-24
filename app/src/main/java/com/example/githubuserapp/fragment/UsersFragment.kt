package com.example.githubuserapp.fragment

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.ListUserAdapter
import com.example.githubuserapp.model.User
import com.example.githubuserapp.utils.SnackbarUtils
import com.example.githubuserapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_users.*

class UsersFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener,
    SearchView.OnQueryTextListener {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: ListUserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupViewModel()
        setupUI()
        setupUsersObservers()
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(mt_main)
        setHasOptionsMenu(true)
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
    }

    private fun setupUI() {
        srl_main.setOnRefreshListener(this)
        isLoading(true)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        rv_main.setHasFixedSize(true)
        rv_main.layoutManager = LinearLayoutManager(context)
        adapter = ListUserAdapter(arrayListOf()) { toDetailFragment(it) }
        rv_main.adapter = adapter
    }

    private fun setupUsersObservers() {
        mainViewModel.setUsers()
        mainViewModel.getUsers().observe(viewLifecycleOwner, Observer {
            isLoading(false)
            adapter.addUsers(it)
        })
        mainViewModel.getMessage().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                isLoading(false)
                SnackbarUtils().errorSnackbar(requireContext(), coordinatorLayout, it)
            }
        })
        mainViewModel.clearMessage()
    }

    private fun setupQueryObservers(username: String?) {
        mainViewModel.setQueryUsers(username)
        mainViewModel.getUsers().observe(viewLifecycleOwner, Observer {
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

    private fun toDetailFragment(user: User) {
        val actionToDetail = UsersFragmentDirections.actionUsersFragmentToDetailActivity()
        actionToDetail.id = user.id
        actionToDetail.username = user.username
        actionToDetail.avatarUrl = user.avatarUrl
        view?.findNavController()?.navigate(actionToDetail)
    }

    private fun toSettingsActivity() {
        view?.findNavController()?.navigate(R.id.action_usersFragment_to_settingsActivity)
    }

    override fun onRefresh() = mainViewModel.setUsers()

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)

        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.action_search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        isLoading(true)
        setupQueryObservers(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean = false

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_settings -> {
                toSettingsActivity()
            }
            R.id.action_favorite -> {
                toFavoriteFragment()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun toFavoriteFragment() {
        view?.findNavController()?.navigate(R.id.action_usersFragment_to_favoriteFragment)
    }

}