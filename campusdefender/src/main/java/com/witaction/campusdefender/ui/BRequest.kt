package com.witaction.campusdefender.ui

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import com.witaction.common.NetConfig
import java.io.Serializable

class BRequest() : Serializable {
    @SerializedName("ApiKey")
    private val apiKey = NetConfig.API_KEY
    @SerializedName("User")
    private val user = JsonObject()
    @SerializedName("Param")
    private val params = HashMap<String, Any>()

    init {
        val userInfo = LocalReponsitory.getUser()
        if (userInfo == null) {
            user.addProperty("SchoolId", LocalReponsitory.getPlat()?.sysID)
            user.addProperty("SelectType", 0)
            user.addProperty("Type", 0)
        } else {
            user.addProperty("Id", userInfo.id)
            user.addProperty("Name", userInfo.name)
            user.addProperty("Phone", userInfo.account)
            user.addProperty("SchoolId", userInfo.schoolId)
            user.addProperty("SelectType", userInfo.selectType)
            user.addProperty("Token", userInfo.token)
            user.addProperty("Type", userInfo.type)
            user.addProperty("UUCAppId", userInfo.uucAppId)
            user.addProperty("UUCAppToken", userInfo.uucAppToken)
            user.addProperty("UUCLoginUrl", userInfo.uucLoginUrl)
        }
        params["CurrentPage"] = 1
        params["PageSize"] = 20
    }

    fun addParams(key: String, value: Any) {
        params[key] = value
    }
}