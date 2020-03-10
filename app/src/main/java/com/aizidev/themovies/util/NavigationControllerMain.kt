package com.aizidev.themovies.util

import androidx.fragment.app.FragmentManager
import com.aizidev.themovies.R
import com.aizidev.themovies.activity.MainActivity
import com.aizidev.themovies.ui.detail.DetailFragment
import com.aizidev.themovies.ui.home.HomeFragment
import javax.inject.Inject

open class NavigationControllerMain @Inject constructor(mainActivity: MainActivity) {

    private var containerId: Int = R.id.f_main_container
    private var fragmentManager: FragmentManager = mainActivity.supportFragmentManager

    fun navigateHome(): Boolean {
        val homeFragment = HomeFragment().newInstance()
        fragmentManager.beginTransaction()
            .replace(containerId, homeFragment)
            .addToBackStack(null)
            .commitAllowingStateLoss()
        return true
    }

    fun navigateDetail(id: Int): Boolean {
        val detailFragment = DetailFragment().newInstance(id)
        fragmentManager.beginTransaction()
            .replace(containerId, detailFragment)
            .addToBackStack(null)
            .commitAllowingStateLoss()
        return true
    }

}