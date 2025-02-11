package com.aizidev.themovies.vo

import androidx.room.Entity
import androidx.room.TypeConverters
import com.aizidev.themovies.db.Converters
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["id"])
@TypeConverters(Converters::class)
data class MovieRes(

    @field:SerializedName("popular")
    var popular: Boolean,

    @field:SerializedName("upcoming")
    var upcoming: Boolean,

    @field:SerializedName("top_rated")
    var topRated: Boolean,

    @field:SerializedName("now_playing")
    var nowPlaying: Boolean,

    @field:SerializedName("overview")
    val overview: String?,

    @field:SerializedName("original_language")
    val originalLanguage: String?,

    @field:SerializedName("original_title")
    val originalTitle: String?,

    @field:SerializedName("video")
    val video: Boolean,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("genre_ids")
    val genreIds: List<Int>,

    @field:SerializedName("poster_path")
    val posterPath: String?,

    @field:SerializedName("backdrop_path")
    val backdropPath: String?,

    @field:SerializedName("release_date")
    val releaseDate: String?,

    @field:SerializedName("popularity")
    val popularity: Double,

    @field:SerializedName("vote_average")
    val voteAverage: Double,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("adult")
    val adult: Boolean,

    @field:SerializedName("vote_count")
    val voteCount: Int,

    @field:SerializedName("favorite")
    val favorite: Boolean = false
)