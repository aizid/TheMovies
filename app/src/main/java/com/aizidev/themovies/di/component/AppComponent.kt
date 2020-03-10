package com.aizidev.themovies.di.component

import android.app.Application
import com.aizidev.themovies.TheMovies
import com.aizidev.themovies.di.builder.ActivityBuilder
import com.aizidev.themovies.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityBuilder::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(application: TheMovies)
}