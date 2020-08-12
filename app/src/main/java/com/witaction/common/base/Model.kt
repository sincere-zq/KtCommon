package com.witaction.common.base

import com.google.gson.annotations.SerializedName
import com.witaction.common.utils.aesEncryptString
import com.witaction.common.utils.e
import java.io.Serializable
import java.net.URLEncoder

data class BLoginReq(val params: MutableMap<String, Any>) : Serializable {


    companion object {
        /**
         * 登录加密密钥
         */
        private const val SIGN_KEY = "008e2caa320947c68322f28e427e0d11"
        /**
         * 统一登录
         */
        private const val SN = "YXWAPP"
        private const val KEY = "1e37ee582427428b8dc0ddc77c6c8c90"
        /**
         * API key
         */
        private const val API_KEY = "jxq_gx_98273"
    }

    constructor() : this(HashMap<String, Any>()) {
        val time: String = KEY + "_" + System.currentTimeMillis()
        val sign = URLEncoder.encode(aesEncryptString(time, SIGN_KEY))
        params.put("sn", SN)
        params.put("sign", sign)
    }

    fun getParam(): String {
        val sb = StringBuffer()
        for (key in params.keys) {
            sb.append(key + "=" + params[key] + "&")
        }
        var re = sb.toString()
        if (re.endsWith("&")) {
            re = re.substring(0, re.length - 1)
        }
        e("Param", re)
        return re
    }

    fun addParam(key: String, value: Any) {
        params[key] = value
    }
}

data class BaseResp<T>(
    @SerializedName("ProResult")
    val code: Int,
    val msg: String,
    @SerializedName("Msg")
    val datas: T?
)

/**
 * 广告
 */
data class AdVert(
    @SerializedName("AdvertName")
    val advertName: String,
    @SerializedName("AdvertDesc")
    val advertDesc: String,
    @SerializedName("AdvertUrl")
    val advertUrl: String,
    @SerializedName("PhotoUrl")
    val photoUrl: String,
    @SerializedName("Sort")
    val sort: String,
    @SerializedName("CreateTime")
    val createTime: String,
    @SerializedName("AdvertType")
    val advertType: Int,
    @SerializedName("StartTime")
    val startTime: String,
    @SerializedName("EndTime")
    val endTime: String
) : Serializable {
    constructor() : this("", "", "", "", "", "", 1, "", "")
}


/**
 * 城市
 */
data class City(
    @SerializedName("CityCode")
    val cityCode: String,
    @SerializedName("CityName")
    val cityName: String
) : Serializable