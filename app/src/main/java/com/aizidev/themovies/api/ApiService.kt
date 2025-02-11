package com.aizidev.themovies.api

import androidx.lifecycle.LiveData
import com.aizidev.themovies.vo.*
import retrofit2.http.*

interface ApiService {

    /*Live Data*/
    @GET("/3/movie/popular")
    fun takeAllMovieByPopularForHome(@Query("api_key") api_key: String): LiveData<ApiResponse<RespOne>>

    @GET("/3/movie/upcoming")
    fun takeAllMovieByUpcomingForHome(@Query("api_key") api_key: String): LiveData<ApiResponse<RespOne>>

    @GET("/3/movie/top_rated")
    fun takeAllMovieByTopRatedForHome(@Query("api_key") api_key: String): LiveData<ApiResponse<RespOne>>

    @GET("/3/movie/now_playing")
    fun takeAllMovieByNowPlayingForHome(@Query("api_key") api_key: String): LiveData<ApiResponse<RespOne>>

    @GET("/3/movie/{movie_id}")
    fun takeDetailMovie(@Path("movie_id") id:Int, @Query("api_key") api_key: String): LiveData<ApiResponse<RespTwo>>

    @GET("/3/movie/{movie_id}/reviews")
    fun takeMovieReview(@Path("movie_id") id:Int, @Query("api_key") api_key: String): LiveData<ApiResponse<RespThree>>

    @GET("/3/search/movie")
    fun searchMovieForHome(@Query("api_key") api_key: String, @Query("query") query: String): LiveData<ApiResponse<RespFour>>

}