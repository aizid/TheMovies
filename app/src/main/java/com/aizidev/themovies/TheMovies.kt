package com.aizidev.themovies

import android.app.Activity
import android.app.Application
import com.aizidev.themovies.di.component.AppInjector
import com.aizidev.themovies.util.CrashReportingTree
import com.facebook.stetho.Stetho
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

class TheMovies : Application(), HasActivityInjector {
    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Stetho.initializeWithDefaults(this)

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        else Timber.plant(CrashReportingTree())

        AppInjector.init(this)
    }

    override fun activityInjector() = activityDispatchingAndroidInjector
}