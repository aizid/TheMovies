package com.aizidev.themovies.repo

import androidx.lifecycle.LiveData
import com.aizidev.themovies.AppExecutors
import com.aizidev.themovies.api.ApiService
import com.aizidev.themovies.db.dao.MovieDao
import com.aizidev.themovies.db.dao.ReviewDao
import com.aizidev.themovies.util.NetworkBoundResource
import com.aizidev.themovies.util.RateLimiter
import com.aizidev.themovies.vo.*
import com.aizidev.themovies.vo.common.Resource
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalRepo @Inject constructor(
    private val appExecutors: AppExecutors,
    private val movieDao: MovieDao,
    private val reviewDao: ReviewDao,
    private val apiService: ApiService
) {
    private val takeListRateLimit = RateLimiter<Any>(10, TimeUnit.MINUTES)

    // FUNCTION LIVE DATA LIST ==========================

    fun loadMoviePopularHome(api_key: String, popular: Boolean): LiveData<Resource<List<MovieRes>>> {
        return object : NetworkBoundResource<List<MovieRes>, RespOne>(appExecutors) {
            override fun saveCallResult(item: RespOne) {
                for (items in item.results) {
                    items.popular = true
                }
                movieDao.insertMovies(item.results)
            }

            override fun shouldFetch(data: List<MovieRes>?): Boolean {
                takeListRateLimit.reset(api_key)
                return data == null || data.isEmpty()
            }

            override fun loadFromDb() = movieDao.loadMoviesPopular(popular)
            override fun createCall() = apiService.takeAllMovieByPopularForHome(api_key)
            override fun onFetchFailed() = takeListRateLimit.reset(api_key)
        }.asLiveData()
    }

    fun loadMovieUpcomingHome(api_key: String, upcomign: Boolean): LiveData<Resource<List<MovieRes>>> {
        return object : NetworkBoundResource<List<MovieRes>, RespOne>(appExecutors) {
            override fun saveCallResult(item: RespOne) {
                for (items in item.results) {
                    items.upcoming = true
                }
                movieDao.insertMovies(item.results)
            }

            override fun shouldFetch(data: List<MovieRes>?): Boolean {
                takeListRateLimit.reset(api_key)
                return data == null || data.isEmpty()
            }

            override fun loadFromDb() = movieDao.loadMoviesUpcoming(upcomign)
            override fun createCall() = apiService.takeAllMovieByUpcomingForHome(api_key)
            override fun onFetchFailed() = takeListRateLimit.reset(api_key)
        }.asLiveData()
    }

    fun loadMovieTopRatedHome(api_key: String, topRated: Boolean): LiveData<Resource<List<MovieRes>>> {
        return object : NetworkBoundResource<List<MovieRes>, RespOne>(appExecutors) {
            override fun saveCallResult(item: RespOne) {
                for (items in item.results) {
                    items.topRated = true
                }
                movieDao.insertMovies(item.results)
            }

            override fun shouldFetch(data: List<MovieRes>?): Boolean {
                takeListRateLimit.reset(api_key)
                return data == null || data.isEmpty()
            }

            override fun loadFromDb() = movieDao.loadMoviesTopRated(topRated)
            override fun createCall() = apiService.takeAllMovieByTopRatedForHome(api_key)
            override fun onFetchFailed() = takeListRateLimit.reset(api_key)
        }.asLiveData()
    }

    fun loadMovieNowPlayingHome(api_key: String, nowPlaying: Boolean): LiveData<Resource<List<MovieRes>>> {
        return object : NetworkBoundResource<List<MovieRes>, RespOne>(appExecutors) {
            override fun saveCallResult(item: RespOne) {
                for (items in item.results) {
                    items.nowPlaying = true
                }
                movieDao.insertMovies(item.results)
            }

            override fun shouldFetch(data: List<MovieRes>?): Boolean {
                takeListRateLimit.reset(api_key)
                return data == null || data.isEmpty()
            }

            override fun loadFromDb() = movieDao.loadMoviesNowPlaying(nowPlaying)
            override fun createCall() = apiService.takeAllMovieByNowPlayingForHome(api_key)
            override fun onFetchFailed() = takeListRateLimit.reset(api_key)
        }.asLiveData()
    }

    fun loadMovieByMovieId(
        token: String, movieId: Int, popular: Boolean,
        upcoming: Boolean, topRated: Boolean, nowPlaying: Boolean
    ): LiveData<Resource<MovieRes>> {
        return object : NetworkBoundResource<MovieRes, RespTwo>(appExecutors) {
            override fun saveCallResult(item: RespTwo) {
                val movieRes = MovieRes(
                    popular, upcoming, topRated, nowPlaying,
                    item.overview, item.originalLanguage,
                    item.originalTitle,
                    item.video!!, item.title, listOf(),
                    item.posterPath, item.backdropPath,
                    item.releaseDate, item.popularity!!, item.voteAverage!!, item.id!!,
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

    fun loadReviewMovieByMovieId(
        token: String, movieId: Int
    ): LiveData<Resource<List<ReviewRes>>> {
        return object : NetworkBoundResource<List<ReviewRes>, RespThree>(appExecutors) {
            override fun saveCallResult(item: RespThree) {
                for (items in item.results) {
                    items.movieId = movieId
                }
                reviewDao.insertReviews(item.results)
            }

            override fun shouldFetch(data: List<ReviewRes>?): Boolean {
                takeListRateLimit.reset(movieId)
                return data == null || data.isEmpty()
            }

            override fun loadFromDb() = reviewDao.loadReviewByMovieId(movieId)
            override fun createCall() = apiService.takeMovieReview(movieId, token)
            override fun onFetchFailed() = takeListRateLimit.reset(movieId)
        }.asLiveData()
    }

    fun loadSearchMovie(api_key: String, query: String): LiveData<Resource<List<MovieRes>>> {
        return object : NetworkBoundResource<List<MovieRes>, RespFour>(appExecutors) {
            override fun saveCallResult(item: RespFour) {
                movieDao.insertMovies(item.results)
            }

            override fun shouldFetch(data: List<MovieRes>?): Boolean {
                takeListRateLimit.reset(api_key)
                return data == null || data.isEmpty()
            }

            override fun loadFromDb() = movieDao.loadSearchMovies(query)
            override fun createCall() = apiService.searchMovieForHome(api_key, query)
            override fun onFetchFailed() = takeListRateLimit.reset(api_key)
        }.asLiveData()
    }

}