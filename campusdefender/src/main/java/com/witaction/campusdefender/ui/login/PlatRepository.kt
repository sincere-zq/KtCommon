package com.witaction.campusdefender.ui.login

import com.witaction.common.NetConfig
import com.witaction.common.base.awaitB

object PlatRepository {
    private val url = NetConfig.BASE_URL_RELEASE
    private val loginApi = PlatServiceCreator.getInstance().create<PlatApi>(url)
    /**
     * 获取城市列表
     */
    suspend fun getCityList() = loginApi.getCityList().awaitB()

    /**
     * 获取平台列表
     */
    suspend fun getPlatList(params: MutableMap<String, Any>) =
        loginApi.getPlatList(getParams(params)).awaitB()

    private fun getParams(params: MutableMap<String, Any>): String {
        val sb = StringBuffer()
        for (key in params.keys) {
            sb.append(key + "=" + params[key] + "&")
        }
        var re = sb.toString()
        if (re.endsWith("&")) {
            re = re.substring(0, re.length - 1)
        }
        return re
    }
}