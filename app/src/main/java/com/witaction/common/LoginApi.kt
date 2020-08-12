package com.witaction.common

import com.witaction.common.base.AdVert
import com.witaction.common.base.BaseResp
import com.witaction.common.base.City
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginApi {
    companion object {
        //获取广告列表
        const val URL_LIST_ADVERT = "WebApi/IMobieApiHandler.ashx?action=GetAdvertList"
        //获取城市列表
        const val URL_LIST_CITY_SERVER = "WebApi/IMobieApiHandler.ashx?action=GetOpenCityList"
        //获取服务器列表
        const val URL_LIST_APP_SERVER = "WebApi/IMobieApiHandler.ashx?action=GetParkSystemList"
    }

    @POST(URL_LIST_ADVERT)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    fun getAdList(@Body params: String): Call<BaseResp<MutableList<AdVert>>>

    @POST(URL_LIST_CITY_SERVER)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    fun getCityList(@Body params: String): Call<BaseResp<MutableList<City>>>

    @POST(URL_LIST_APP_SERVER)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    fun getServerList(@Body params: String): Call<Any>
}