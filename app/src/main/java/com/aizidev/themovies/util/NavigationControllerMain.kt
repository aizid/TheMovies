package com.aizidev.themovies.util

import androidx.fragment.app.FragmentManager
import com.aizidev.themovies.R
import com.aizidev.themovies.activity.MainActivity
import com.aizidev.themovies.ui.detail.DetailFragment
import com.aizidev.themovies.ui.home.HomeFragment
import com.aizidev.themovies.ui.search.SearchFragment
import javax.inject.Inject

open class NavigationControllerMain @Inject constructor(mainActivity: MainActivity) {

    private var containerId: Int = R.id.f_main_container
    private var fragmentManager: FragmentManager = mainActivity.supportFragmentManager

    fun navigateHome() {
        val homeFragment = HomeFragment().newInstance()
        fragmentManager.beginTransaction()
            .replace(containerId, homeFragment)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    fun navigateDetail(id: Int, popular: Boolean, upcoming: Boolean,
                       topRated: Boolean, nowPlaying: Boolean) {
        val detailFragment = DetailFragment().newInstance(id, popular, upcoming, topRated, nowPlaying)
        fragmentManager.beginTransaction()
            .replace(containerId, detailFragment)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    fun navigateSearch() {
        val searchFragment = SearchFragment().newInstance()
        fragmentManager.beginTransaction()
            .replace(containerId, searchFragment)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

}