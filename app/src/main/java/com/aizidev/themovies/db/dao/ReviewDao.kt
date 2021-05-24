package com.aizidev.themovies.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aizidev.themovies.vo.ReviewRes

@Dao
abstract class ReviewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertReviews(postReviewRes: List<ReviewRes>)

    @Query("SELECT * FROM ReviewRes")
    abstract fun loadReviews(): LiveData<List<ReviewRes>>

    @Query("SELECT * FROM ReviewRes WHERE movieId = :movieId")
    abstract fun loadReviewByMovieId(movieId: Int): LiveData<List<ReviewRes>>
}