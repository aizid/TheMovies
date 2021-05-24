package com.aizidev.themovies.vo

import com.google.gson.annotations.SerializedName

data class RespOne(

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("total_pages")
	val totalPages: Int? = null,

	@field:SerializedName("results")
	var results: List<MovieRes>,

	@field:SerializedName("total_results")
	val totalResults: Int? = null
)