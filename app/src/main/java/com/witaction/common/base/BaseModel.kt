package com.witaction.common.base

import com.google.gson.annotations.SerializedName

data class BaseResp<T>(
    @SerializedName("ProResult")
    val code: Int,
    val msg: String,
    @SerializedName("Msg")
    val datas: T?
)