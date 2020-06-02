package com.example.githubuserapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(private var fragmentTitles: ArrayList<String>, private var fragments: ArrayList<Fragment>,
                       fm: FragmentManager, behavior: Int
): FragmentPagerAdapter(fm, behavior) {

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitles[position]
    }

}
