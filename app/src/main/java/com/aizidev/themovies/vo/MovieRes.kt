package com.aizidev.themovies.vo

import androidx.room.Entity
import androidx.room.TypeConverters
import com.aizidev.themovies.db.Converters
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["idPosCode"])
@TypeConverters(Converters::class)
data class MovieRes(

    @field:SerializedName("codeCountry")
    val codeCountry: String? = null,

    @field:SerializedName("createdDate")
    val createdDate: String? = null,

    @field:SerializedName("infoStatus")
    val infoStatus: Int? = null,

    @field:SerializedName("numberProvince")
    val numberProvince: Int? = null,

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("numberPosCode")
    val numberPosCode: String? = null,

    @field:SerializedName("nameCity")
    val nameCity: String? = null,

    @field:SerializedName("numberCity")
    val numberCity: Int? = null,

    @field:SerializedName("idPosCode")
    val idPosCode: Int? = null
)