package com.aizidev.themovies.db

import androidx.room.TypeConverter
import timber.log.Timber
import java.util.*

object Converters {
    @TypeConverter
    @JvmStatic
    fun stringToIntList(data: String?): List<Int>? {
        return if (data != null || data?.length != 0) {
            listOf()
        } else {
            data?.let { it ->
                it.split(",").map {
                    try {
                        it.toInt()
                    } catch (ex: NumberFormatException) {
                        Timber.e(ex, "Cannot convert $it to number")
                        null
                    }
                }
            }?.filterNotNull()
        }
    }

    @TypeConverter
    @JvmStatic
    fun intListToString(ints: List<Int>?): String? {
        return ints?.joinToString(",")
    }

    @TypeConverter
    @JvmStatic
    fun stringToStringList(data: String?): List<String>? {
        return data?.let { it ->
            it.split(",").map {
                try {
                    it
                } catch (ex: NumberFormatException) {
                    Timber.e(ex, "Cannot convert $it to number")
                    null
                }
            }
        }?.filterNotNull()
    }

    @TypeConverter
    @JvmStatic
    fun stringListToString(string: List<String>?): String? {
        return string?.joinToString(",")
    }
}