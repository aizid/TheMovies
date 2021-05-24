package com.aizidev.themovies.vo

import androidx.room.Entity
import androidx.room.TypeConverters
import com.aizidev.themovies.db.Converters
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["id"])
@TypeConverters(Converters::class)
data class ReviewRes(

	@field:SerializedName("author")
	val author: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("movieId")
    var movieId: Int,

	@field:SerializedName("content")
	val content: String,

	@field:SerializedName("url")
	val url: String
)