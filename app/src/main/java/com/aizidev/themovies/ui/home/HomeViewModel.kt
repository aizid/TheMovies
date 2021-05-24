package com.aizidev.themovies.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.aizidev.themovies.repo.GlobalRepo
import com.aizidev.themovies.util.AbsentLiveData
import com.aizidev.themovies.vo.MovieRes
import com.aizidev.themovies.vo.common.Resource
import javax.inject.Inject

class HomeViewModel
@Inject constructor(globalRepo: GlobalRepo) : ViewModel() {

    private val _token = MutableLiveData<String>()

    val token: LiveData<String>
        get() = _token
    val popularMovie: LiveData<Resource<List<MovieRes>>> = Transformations
        .switchMap(_token) { token ->
            if (token == null) {
                AbsentLiveData.create()
            } else {
                globalRepo.loadMoviePopularHome(token, true)
            }
        }
    val upcomingMovie: LiveData<Resource<List<MovieRes>>> = Transformations
        .switchMap(_token) { token ->
            if (token == null) {
                AbsentLiveData.create()
            } else {
                globalRepo.loadMovieUpcomingHome(token, true)
            }
        }
    val topratedMovie: LiveData<Resource<List<MovieRes>>> = Transformations
        .switchMap(_token) { token ->
            if (token == null) {
                AbsentLiveData.create()
            } else {
                globalRepo.loadMovieTopRatedHome(token, true)
            }
        }
    val nowplayingMovie: LiveData<Resource<List<MovieRes>>> = Transformations
        .switchMap(_token) { token ->
            if (token == null) {
                AbsentLiveData.create()
            } else {
                globalRepo.loadMovieNowPlayingHome(token, true)
            }
        }

    fun setToken(token: String?) {
        if (_token.value != token) {
            _token.value = token
        }
    }

    fun retry() {
        _token.value?.let {
            _token.value = it
        }
    }
}
