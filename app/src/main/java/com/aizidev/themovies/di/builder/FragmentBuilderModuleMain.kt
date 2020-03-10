package com.aizidev.themovies.di.builder

import com.aizidev.themovies.ui.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuilderModuleMain {

    @ContributesAndroidInjector
    abstract fun contributeHome(): HomeFragment

}