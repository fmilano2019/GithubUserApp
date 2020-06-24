package com.example.githubuserapp.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.ViewPagerAdapter
import com.example.githubuserapp.fragment.FollowersFragment
import com.example.githubuserapp.fragment.FollowingFragment
import com.example.githubuserapp.fragment.ReposFragment
import com.example.githubuserapp.model.User
import com.example.githubuserapp.utils.SnackbarUtils
import com.example.githubuserapp.viewmodel.DetailViewModel
import com.google.android.material.button.MaterialButtonToggleGroup
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), View.OnClickListener,
    MaterialButtonToggleGroup.OnButtonCheckedListener {

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var  reposFragment: ReposFragment
    private lateinit var  followersFragment: FollowersFragment
    private lateinit var  followingFragment: FollowingFragment
    private lateinit var  viewPagerAdapter: ViewPagerAdapter
    private lateinit var user: User
    private var isIdAvailable: Boolean? = null
    private val args: DetailActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setupUI()
        setupViewModel()
        isFavoriteCheck()
        setupUserObserver()
    }

    private fun isFavoriteCheck() {
        val id = args.id
        detailViewModel.setFavorite(this, id)
        detailViewModel.getFavorite().observe(this, Observer {
            if(it != null) {
                btn_detail_favorite.isChecked = true
                isIdAvailable = true
            } else {
                isIdAvailable = false
            }
        })
    }

    private fun setupViewModel() {
        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
    }

    private fun setupUI() {
        mt_detail.setNavigationOnClickListener(this)
        btg_detail_favorite.addOnButtonCheckedListener(this)
        isLoading(true)
        setupTabViewPager()
    }

    private fun isLoading(state: Boolean) {
        when(state) {
            true -> {
                pb_detail.visibility = View.VISIBLE
            }
            false -> {
                pb_detail.visibility = View.GONE
            }
        }
    }

    private fun setupUserObserver() {
        val id = args.id
        val username = args.username
        val avatarUrl = args.avatarUrl
        user = User(id, username, avatarUrl, null, null, null, null, null, null)
        username.let {
            detailViewModel.setUser(it)
            detailViewModel.setRepos(it)
            detailViewModel.setFollowers(it)
            detailViewModel.setFollowing(it)
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
            if (it != null) {
                isLoading(false)
                SnackbarUtils().errorSnackbar(this, coordinatorLayout, it)
            }
        })
        detailViewModel.clearMessage()
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

    override fun onButtonChecked(
        group: MaterialButtonToggleGroup?,
        checkedId: Int,
        isChecked: Boolean
    ) {
        when(checkedId) {
            R.id.btn_detail_favorite -> {
                when(isChecked) {
                    true -> {
                        if (isIdAvailable == false) {
                            detailViewModel.insertFavorite(this, user)
                        }
                        btn_detail_favorite.setBackgroundColor(resources.getColor(R.color.colorAccent))
                        btn_detail_favorite.text = resources.getString(R.string.favorited)
                    }
                    false -> {
                        if (isIdAvailable == true) {
                            detailViewModel.deleteFavorite(this, user)
                        }
                        btn_detail_favorite.setBackgroundColor(resources.getColor(R.color.colorPrimaryLight))
                        btn_detail_favorite.text = resources.getString(R.string.favorite)
                    }
                }
            }
        }
    }

}
