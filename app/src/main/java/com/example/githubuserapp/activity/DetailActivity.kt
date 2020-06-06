package com.example.githubuserapp.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.ViewPagerAdapter
import com.example.githubuserapp.fragment.FollowersFragment
import com.example.githubuserapp.fragment.FollowingFragment
import com.example.githubuserapp.fragment.ReposFragment
import com.example.githubuserapp.model.User
import com.example.githubuserapp.util.snackbar
import com.example.githubuserapp.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val INTENT_DATA = "intent_data"
    }

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var  reposFragment: ReposFragment
    private lateinit var  followersFragment: FollowersFragment
    private lateinit var  followingFragment: FollowingFragment
    private lateinit var  viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setupViewModel()
        setupUI()
        setupUserObserver()
    }

    private fun setupViewModel() {
        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
    }

    private fun setupUI() {
        pb_detail.visibility = View.VISIBLE
        tb_detail.setNavigationOnClickListener(this)
        setupTabViewPager()
    }

    private fun setupTabViewPager() {
        reposFragment = ReposFragment()
        followersFragment = FollowersFragment()
        followingFragment = FollowingFragment()
        val pagerTitles = arrayListOf(
            resources.getString(R.string.tv_repositories),
            resources.getString(R.string.tv_followers),
            resources.getString(R.string.tv_following)
        )
        val pagerFragments = arrayListOf(reposFragment, followersFragment, followingFragment)
        viewPagerAdapter = ViewPagerAdapter(
            pagerTitles,
            pagerFragments,
            supportFragmentManager,
            0
        )
        pager_detail.adapter = viewPagerAdapter
        tab_detail.setupWithViewPager(pager_detail)
    }

    private fun setupUserObserver() {
        val username = intent.getStringExtra(INTENT_DATA)
        username?.let {
            detailViewModel.setUser(username)
            detailViewModel.setRepos(username)
            detailViewModel.setFollowers(username)
            detailViewModel.setFollowing(username)
        }
        detailViewModel.getUser().observe(this, Observer {
            tv_detail_name.text = it.name
            tv_detail_username.text = it.username
            tv_detail_company.text = it.company
            tv_detail_location.text = it.location
            tv_detail_repository.text = it.repos.toString()
            tv_detail_follower.text = it.followers.toString()
            tv_detail_following.text = it.following.toString()
            Glide.with(this)
                .load(it.avatarUrl)
                .into(iv_detail_avatar)
            setVisibility(it)
        })
        detailViewModel.getMessage().observe(this, Observer {
            pb_detail.visibility = View.GONE
            snackbar(coordinatorLayout, it)
        })
    }

    private fun setVisibility(it: User) {
        pb_detail.visibility = View.GONE
        tv_detail_tag_username.visibility = View.VISIBLE
        tv_detail_tag_repo.visibility = View.VISIBLE
        tv_detail_tag_followers.visibility = View.VISIBLE
        tv_detail_tag_following.visibility = View.VISIBLE

        if (!it.company.isNullOrEmpty()) {
            iv_detail_company.visibility = View.VISIBLE
            tv_detail_company.visibility = View.VISIBLE
        } else {
            iv_detail_company.visibility = View.GONE
            tv_detail_company.visibility = View.GONE
        }

        if(!it.location.isNullOrEmpty()) {
            iv_detail_location.visibility = View.VISIBLE
            tv_detail_location.visibility = View.VISIBLE
        } else {
            iv_detail_location.visibility = View.GONE
            tv_detail_location.visibility = View.GONE
        }
    }

    override fun onClick(v: View?) {
        onBackPressed()
    }

}
