package com.aizidev.themovies.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.aizidev.themovies.repo.GlobalRepo
import com.aizidev.themovies.util.AbsentLiveData
import com.aizidev.themovies.vo.MovieRes
import com.aizidev.themovies.vo.common.Resource
import javax.inject.Inject

class SearchViewModel
@Inject constructor(globalRepo: GlobalRepo) : ViewModel() {

    private val _token = MutableLiveData<String>()
    private val _query = MutableLiveData<String>()

    val token: LiveData<String>
        get() = _token
    val query: LiveData<String>
        get() = _query

    val searchMovie: LiveData<Resource<List<MovieRes>>> = Transformations
        .switchMap(_query) { query ->
            if (query.length < 3 || token.value == null) {
                AbsentLiveData.create()
            } else {
                globalRepo.loadSearchMovie(token.value!!, query)
            }
        }

    fun setToken(token: String?) {
        if (_token.value != token) {
            _token.value = token
        }
    }

    fun setQuery(query: String?) {
        if (_query.value != query) {
            _query.value = "$query%"
        }
    }

    fun retry() {
        _token.value?.let {
            _token.value = it
        }
    }
}