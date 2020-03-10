package com.aizidev.themovies.util

import androidx.fragment.app.FragmentManager
import com.aizidev.themovies.R
import com.aizidev.themovies.activity.MainActivity
import javax.inject.Inject

open class NavigationControllerMain @Inject constructor(mainActivity: MainActivity) {

    private var containerId: Int = R.id.f_main_container
    private var fragmentManager: FragmentManager = mainActivity.supportFragmentManager
}