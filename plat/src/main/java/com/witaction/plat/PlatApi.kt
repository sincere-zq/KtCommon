package com.witaction.plat

import com.witaction.common.base.BaseResp
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PlatApi {
    companion object {
        const val URL_LIST_CITY_SERVER =
            "IMobieApiHandler.ashx?action=GetOpenCityList" //获取城市列表(调用过)
        const val URL_LIST_APP_SERVER =
            "IMobieApiHandler.ashx?action=GetParkSystemList" //获取服务器列表(调用过)
    }

    @POST(URL_LIST_CITY_SERVER)
    fun getCityList(): Call<BaseResp<List<City>>>

    @POST(URL_LIST_APP_SERVER)
    fun getPlatList(@Body params: MutableMap<String,Any>): Call<BaseResp<List<Plat>>>
}