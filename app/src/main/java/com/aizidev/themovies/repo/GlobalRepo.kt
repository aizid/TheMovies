package com.aizidev.themovies.repo

import androidx.lifecycle.LiveData
import com.aizidev.themovies.AppExecutors
import com.aizidev.themovies.api.ApiService
import com.aizidev.themovies.db.dao.MovieDao
import com.aizidev.themovies.util.NetworkBoundResource
import com.aizidev.themovies.util.RateLimiter
import com.aizidev.themovies.vo.*
import com.aizidev.themovies.vo.common.Resource
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
    private val movieDao: MovieDao,
    private val apiService: ApiService
) {
    private val takeListRateLimit = RateLimiter<Any>(10, TimeUnit.MINUTES)

    // FUNCTION LIVE DATA LIST ==========================

    fun loadMoviewPopularHome(api_key: String): LiveData<Resource<List<MovieRes>>> {
        return object : NetworkBoundResource<List<MovieRes>, RespOne>(appExecutors) {
            override fun saveCallResult(item: RespOne) {
                movieDao.insertMovies(item.results)
            }

            override fun shouldFetch(data: List<MovieRes>?): Boolean {
                takeListRateLimit.reset(api_key)
                return data == null || data.isEmpty()
            }

            override fun loadFromDb() = movieDao.loadMovies()
            override fun createCall() = apiService.takeAllMovieByPopularForHome(api_key)
            override fun onFetchFailed() = takeListRateLimit.reset(api_key)
        }.asLiveData()
    }

    fun loadMoviewTopRatedHome(api_key: String): LiveData<Resource<List<MovieRes>>> {
        return object : NetworkBoundResource<List<MovieRes>, RespOne>(appExecutors) {
            override fun saveCallResult(item: RespOne) {
                movieDao.insertMovies(item.results)
            }

            override fun shouldFetch(data: List<MovieRes>?): Boolean {
                takeListRateLimit.reset(api_key)
                return data == null || data.isEmpty()
            }

            override fun loadFromDb() = movieDao.loadMovies()
            override fun createCall() = apiService.takeAllMovieByTopRatedForHome(api_key)
            override fun onFetchFailed() = takeListRateLimit.reset(api_key)
        }.asLiveData()
    }

    fun loadMoviewUpcomingHome(api_key: String): LiveData<Resource<List<MovieRes>>> {
        return object : NetworkBoundResource<List<MovieRes>, RespOne>(appExecutors) {
            override fun saveCallResult(item: RespOne) {
                movieDao.insertMovies(item.results)
            }

            override fun shouldFetch(data: List<MovieRes>?): Boolean {
                takeListRateLimit.reset(api_key)
                return data == null || data.isEmpty()
            }

            override fun loadFromDb() = movieDao.loadMovies()
            override fun createCall() = apiService.takeAllMovieByUpcomingForHome(api_key)
            override fun onFetchFailed() = takeListRateLimit.reset(api_key)
        }.asLiveData()
    }

    fun loadMoviewNowPlayingHome(api_key: String): LiveData<Resource<List<MovieRes>>> {
        return object : NetworkBoundResource<List<MovieRes>, RespOne>(appExecutors) {
            override fun saveCallResult(item: RespOne) {
                movieDao.insertMovies(item.results)
            }

            override fun shouldFetch(data: List<MovieRes>?): Boolean {
                takeListRateLimit.reset(api_key)
                return data == null || data.isEmpty()
            }

            override fun loadFromDb() = movieDao.loadMovies()
            override fun createCall() = apiService.takeAllMovieByNowPlayingForHome(api_key)
            override fun onFetchFailed() = takeListRateLimit.reset(api_key)
        }.asLiveData()
    }

    fun loadMovieByMovieId(
        token: String, movieId: Int
    ): LiveData<Resource<MovieRes>> {
        return object : NetworkBoundResource<MovieRes, RespTwo>(appExecutors) {
            override fun saveCallResult(item: RespTwo) {
                val movieRes = MovieRes(
                    item.overview.toString(), item.originalLanguage.toString(),
                    item.originalTitle.toString(),
                    item.video!!, item.title.toString(), listOf(),
                    item.posterPath.toString(), item.backdropPath.toString(),
                    item.releaseDate.toString(), item.popularity!!, item.voteAverage!!, item.id!!,
                    item.adult!!, item.voteCount!!, false
                )
                movieDao.insertMovie(movieRes)
            }

            override fun shouldFetch(data: MovieRes?): Boolean {
                return data == null || takeListRateLimit.shouldFetch(movieId)
            }

            override fun loadFromDb() = movieDao.loadDetailMovies(movieId)

            override fun createCall() = apiService.takeDetailMovie(
                movieId, token
            )

            override fun onFetchFailed() {
                takeListRateLimit.shouldFetch(movieId)
            }

        }.asLiveData()
    }

//    fun loadReviewMovieByMovieId(
//        token: String, movieId: Int
//    ): LiveData<Resource<List<ResultsItem>>> {
//        return object : NetworkBoundResource<List<MovieRes>, RespThree>(appExecutors) {
//            override fun saveCallResult(item: RespThree) {
//                movieDao.insertMovie(movieRes)
//            }
//
//            override fun shouldFetch(data: MovieRes?): Boolean {
//                return data == null || takeListRateLimit.shouldFetch(movieId)
//            }
//
//            override fun loadFromDb() = movieDao.loadDetailMovies(movieId)
//
//            override fun createCall() = apiService.takeDetailMovie(
//                movieId, token
//            )
//
//            override fun onFetchFailed() {
//                takeListRateLimit.shouldFetch(movieId)
//            }
//
//        }.asLiveData()
//    }

}