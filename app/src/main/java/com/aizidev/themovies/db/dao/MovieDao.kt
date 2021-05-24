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
    abstract fun insertMovies(postHomeRes: List<MovieRes>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMovie(repos: MovieRes)

    @Query("SELECT * FROM MovieRes")
    abstract fun loadMovies(): LiveData<List<MovieRes>>

    @Query("SELECT * FROM MovieRes WHERE popular = :popular")
    abstract fun loadMoviesPopular(popular: Boolean): LiveData<List<MovieRes>>

    @Query("SELECT * FROM MovieRes WHERE upcoming = :upcoming")
    abstract fun loadMoviesUpcoming(upcoming: Boolean): LiveData<List<MovieRes>>

    @Query("SELECT * FROM MovieRes WHERE topRated = :topRated")
    abstract fun loadMoviesTopRated(topRated: Boolean): LiveData<List<MovieRes>>

    @Query("SELECT * FROM MovieRes WHERE nowPlaying = :nowPlaying")
    abstract fun loadMoviesNowPlaying(nowPlaying: Boolean): LiveData<List<MovieRes>>

    @Query("SELECT * FROM MovieRes WHERE id = :id")
    abstract fun loadDetailMovies(id: Int): LiveData<MovieRes>

    @Query("SELECT * FROM MovieRes WHERE favorite = :favorite")
    abstract fun loadFavoriteMovies(favorite: Boolean): LiveData<List<MovieRes>>

    @Query("SELECT * FROM MovieRes WHERE title LIKE :query")
    abstract fun loadSearchMovies(query: String): LiveData<List<MovieRes>>
}