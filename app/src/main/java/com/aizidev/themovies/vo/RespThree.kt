package com.aizidev.themovies.vo

import com.google.gson.annotations.SerializedName

data class RespThree(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("page")
	val page: Int,

	@field:SerializedName("total_pages")
	val totalPages: Int,

	@field:SerializedName("results")
    var results: List<ReviewRes>,

	@field:SerializedName("total_results")
	val totalResults: Int
)