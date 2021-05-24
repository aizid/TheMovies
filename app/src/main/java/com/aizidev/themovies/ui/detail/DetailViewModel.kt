package com.aizidev.themovies.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.aizidev.themovies.repo.GlobalRepo
import com.aizidev.themovies.util.AbsentLiveData
import com.aizidev.themovies.vo.MovieRes
import com.aizidev.themovies.vo.ReviewRes
import com.aizidev.themovies.vo.common.Resource
import javax.inject.Inject

class DetailViewModel
@Inject constructor(val globalRepo: GlobalRepo) : ViewModel() {

    private val _token = MutableLiveData<String>()
    private val _movieId = MutableLiveData<Int>()
    private val _popular = MutableLiveData<Boolean>()
    private val _upcoming = MutableLiveData<Boolean>()
    private val _topRated = MutableLiveData<Boolean>()
    private val _nowPlaying = MutableLiveData<Boolean>()

    val token: LiveData<String>
        get() = _token
    val movieId: LiveData<Int>
        get() = _movieId
    val popular: LiveData<Boolean>
        get() = _popular
    val upcoming: LiveData<Boolean>
        get() = _upcoming
    val topRated: LiveData<Boolean>
        get() = _topRated
    val nowPlaying: LiveData<Boolean>
        get() = _nowPlaying

    val movieDetail: LiveData<Resource<MovieRes>> = Transformations
        .switchMap(_token) { token ->
            if (token == null) {
                AbsentLiveData.create()
            } else {
                globalRepo.loadMovieByMovieId(token, movieId.value!!, popular.value!!, upcoming.value!!, topRated.value!!, nowPlaying.value!!)
            }
        }

    val reviewMovie: LiveData<Resource<List<ReviewRes>>> = Transformations
        .switchMap(_token) { token ->
            if (token == null) {
                AbsentLiveData.create()
            } else {
                globalRepo.loadReviewMovieByMovieId(token, movieId.value!!)
            }
        }

    fun setToken(token: String?) {
        if (_token.value != token) {
            _token.value = token
        }
    }
    fun setMovieId(movieId: Int?) {
        if (_movieId.value != movieId) {
            _movieId.value = movieId
        }
    }
    fun setPopular(popular: Boolean?) {
        if (_popular.value != popular) {
            _popular.value = popular
        }
    }
    fun setUpcoming(upcoming: Boolean?) {
        if (_upcoming.value != upcoming) {
            _upcoming.value = upcoming
        }
    }
    fun setTopRated(topRated: Boolean?) {
        if (_topRated.value != topRated) {
            _topRated.value = topRated
        }
    }
    fun setNowPlaying(nowPlaying: Boolean?) {
        if (_nowPlaying.value != nowPlaying) {
            _nowPlaying.value = nowPlaying
        }
    }

    fun retry() {
        _token.value?.let {
            _token.value = it
        }
    }
}
