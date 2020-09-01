package com.witaction.common.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    @SerializedName("Id") val id: String,
    @SerializedName("Account") val account: String,
    @SerializedName("SchoolLogo") val schoolLogo: String,
    @SerializedName("Token") val token: String,
    @SerializedName("Type") val type: Int,
    @SerializedName("Name") val name: String,
    var selectType: Int,
    @SerializedName("UUCAppToken") val uucAppToken: String,
    @SerializedName("UUCAppId") val uucAppId: String,
    @SerializedName("UUCLoginUrl") val uucLoginUrl: String,
    @SerializedName("AvatarUrl") val avatarUrl: String,
    @SerializedName("EmailLoginUrl") val emailLoginUrl: String,
    @SerializedName("UUCImFileUploadUrl") val uucImFileUploadUrl: String,
    @SerializedName("HasPwd") val hasPwd: Int,
    @SerializedName("SchoolOaUrl") val schoolOaUrl: String,
    @SerializedName("UUCApiUrl") val uucApiUrl: String,
    @SerializedName("Gender") val gender: String,
    @SerializedName("Remark") val remark: String,
    var schoolId: String
) : Serializable