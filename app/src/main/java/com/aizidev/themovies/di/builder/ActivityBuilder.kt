package com.aizidev.themovies.di.builder

import com.aizidev.themovies.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [FragmentBuilderModuleMain::class])
    abstract fun contributeMainActivity(): MainActivity

}