package com.aizidev.themovies.vo

import com.google.gson.annotations.SerializedName

data class RespFour(

	@field:SerializedName("page")
	val page: Int,

	@field:SerializedName("total_pages")
	val totalPages: Int,

	@field:SerializedName("results")
	val results: List<MovieRes>,

	@field:SerializedName("total_results")
	val totalResults: Int
)
