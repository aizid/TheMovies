package com.aizidev.themovies.repo

import androidx.lifecycle.LiveData
import com.aizidev.themovies.AppExecutors
import com.aizidev.themovies.api.ApiService
import com.aizidev.themovies.util.RateLimiter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalRepo @Inject constructor(
    private val appExecutors: AppExecutors,
    private val apiService: ApiService
) {
    private val takeListRateLimit = RateLimiter<Any>(10, TimeUnit.MINUTES)

    // FUNCTION LIVE DATA LIST ==========================

}