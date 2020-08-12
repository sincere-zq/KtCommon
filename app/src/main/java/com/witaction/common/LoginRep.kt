package com.witaction.common

import com.witaction.common.base.*

object LoginRep {
    private const val URL = "http://home.sgurad.com:8012/"
    private val loginApi = ServiceCreator.create(URL, LoginApi::class.java)

    suspend fun getCityList(): BaseResp<MutableList<City>> {
        return loginApi.getCityList(BLoginReq().getParam()).await()
    }

}