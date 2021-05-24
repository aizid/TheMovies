package com.aizidev.themovies.di.builder

import com.aizidev.themovies.ui.detail.DetailFragment
import com.aizidev.themovies.ui.home.HomeFragment
import com.aizidev.themovies.ui.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuilderModuleMain {

    @ContributesAndroidInjector
    abstract fun contributeHome(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeDetail(): DetailFragment

    @ContributesAndroidInjector
    abstract fun contributeSearch(): SearchFragment

}