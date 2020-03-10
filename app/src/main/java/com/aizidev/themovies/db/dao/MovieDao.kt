package com.aizidev.themovies.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aizidev.themovies.vo.MovieRes

@Dao
abstract class MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertPosCodes(postHomeRes: List<MovieRes>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertPosCode(repos: MovieRes)

    @Query("SELECT * FROM MovieRes")
    abstract fun loadPostHome(): LiveData<List<MovieRes>>

    @Query("SELECT * FROM MovieRes WHERE numberPosCode = :numberPosCode")
    abstract fun loadPostHomeByNumberPosCode(numberPosCode: String): LiveData<MovieRes>
}