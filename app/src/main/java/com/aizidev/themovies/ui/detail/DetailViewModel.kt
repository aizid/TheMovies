package com.aizidev.themovies.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.aizidev.themovies.repo.GlobalRepo
import com.aizidev.themovies.util.AbsentLiveData
import com.aizidev.themovies.vo.MovieRes
import com.aizidev.themovies.vo.RespTwo
import com.aizidev.themovies.vo.common.Resource
import javax.inject.Inject

class DetailViewModel
@Inject constructor(val globalRepo: GlobalRepo) : ViewModel() {

    private val _token = MutableLiveData<String>()
    private val _memberacc = MutableLiveData<MemberAcc>()

    val token: LiveData<String>
        get() = _token
    val memberacc: LiveData<MemberAcc>
        get() = _memberacc

    val memberAcc: LiveData<Resource<MovieRes>> = Transformations
        .switchMap(_memberacc) { input ->
            input.ifExists { token, memberId ->
                globalRepo.loadMovieByMovieId(token, memberId)
            }
        }

//    val reviewMovie: LiveData<Resource<List<MovieRes>>> = Transformations
//        .switchMap(_token) { token ->
//            if (token == null) {
//                AbsentLiveData.create()
//            } else {
//                globalRepo.(token)
//            }
//        }

    fun setToken(token: String?) {
        if (_token.value != token) {
            _token.value = token
        }
    }
    fun setMemberAcc(
        token: String,
        memberId: Int
    ) {
        val update = MemberAcc(token, memberId)
        if (_memberacc.value == update){
            return
        } else{
            _memberacc.value = update
        }
    }

    fun retry() {
        _token.value?.let {
            _token.value = it
        }
        val tokenMemberAcc = _memberacc.value?.token
        val memberId = _memberacc.value?.memberId
        if (tokenMemberAcc != null && memberId != null) {
            _memberacc.value = MemberAcc(tokenMemberAcc, memberId)
        }
    }

    data class MemberAcc(
        val token: String,
        val memberId: Int
    ) {
        fun <T> ifExists(f: (String, Int) -> LiveData<T>): LiveData<T> {
            return if (token.isBlank() || memberId == 0) {
                AbsentLiveData.create()
            } else {
                f(token, memberId)
            }
        }
    }
}
