package com.aizidev.themovies.vo.common

import com.aizidev.themovies.vo.common.Status.SUCCESS
import com.aizidev.themovies.vo.common.Status.LOADING
import com.aizidev.themovies.vo.common.Status.ERROR

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }
}