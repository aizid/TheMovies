package com.aizidev.themovies.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aizidev.themovies.vo.MovieRes

@Dao
abstract class FavoritDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMovies(postHomeRes: List<MovieRes>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMovie(repos: MovieRes)

    @Query("SELECT * FROM MovieRes")
    abstract fun loadMovies(): LiveData<List<MovieRes>>

    @Query("SELECT * FROM MovieRes WHERE id = :id")
    abstract fun loadDetailMovies(id: Int): LiveData<MovieRes>
}