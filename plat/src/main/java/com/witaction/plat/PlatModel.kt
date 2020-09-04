package com.witaction.plat

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class City(
    @SerializedName("CityCode") val cityCode: String,
    @SerializedName("CityName") val cityName: String
) : Serializable

data class Plat(
    @SerializedName("Name") val name: String,
    @SerializedName("LoginIPAddr") val loginIPAddr: String,
    @SerializedName("ModIPAddr") val modIPAddr: String,
    @SerializedName("Latitude") val latitude: String,
    @SerializedName("Longitude") val longitude: String,
    @SerializedName("CityCode") val cityCode: String,
    @SerializedName("Address") val address: String,
    @SerializedName("SysID") val sysID: String
) : Serializable