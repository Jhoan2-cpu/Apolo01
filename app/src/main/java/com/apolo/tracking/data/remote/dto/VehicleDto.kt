package com.apolo.tracking.data.remote.dto

import com.google.gson.annotations.SerializedName

data class VehicleDto(
    @SerializedName("id")        val id: Int?,
    @SerializedName("plate")     val plate: String?,
    @SerializedName("speed")     val speed: String?,
    @SerializedName("latitude")  val latitude: Double?,
    @SerializedName("longitude") val longitude: Double?,
    @SerializedName("angle")     val angle: Float?,
    @SerializedName("status")    val status: String?
)
