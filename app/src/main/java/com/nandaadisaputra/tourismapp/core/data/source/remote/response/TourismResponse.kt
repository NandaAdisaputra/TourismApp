package com.nandaadisaputra.tourismapp.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

//data class TourismResponse(
//    val id: String,
//    val name: String,
//    val description: String,
//    val address: String,
//    val longitude: Double,
//    val latitude: Double,
//    val like: Int,
//    val image: String
//)
data class TourismResponse(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("address")
    val address: String,

    @field:SerializedName("longitude")
    val longitude: Double,

    @field:SerializedName("latitude")
    val latitude: Double,

    @field:SerializedName("like")
    val like: Int,

    @field:SerializedName("image")
    val image: String
)