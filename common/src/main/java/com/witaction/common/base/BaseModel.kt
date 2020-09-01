package com.witaction.common.base

import com.google.gson.annotations.SerializedName

data class BaseResp<T>(
    @SerializedName("ProResult")
    val code: Int,
    val msg: String,
    @SerializedName("Msg")
    val datas: T?
)


data class BResp<T>(
    @SerializedName("IsSuccess")
    val isSuccess: Int,
    @SerializedName("Message")
    val msg: String,
    @SerializedName("ErrorCode")
    val errorCode: String,
    @SerializedName("Data")
    val data: MutableList<T>?
) {
    fun isSuccess() = isSuccess == 1
    fun getSimpleData(): T? = data?.get(0)?.let { return@let it }
}