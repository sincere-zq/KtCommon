package com.witaction.yunxiaowei.framwork

object ServerRepository {
    private lateinit var api: ServerApi
    fun initApi(url: String) {
        api = ServerCreator.getInstance().create(url)
    }

    suspend fun loginByAccount(params: MutableMap<String, Any>) = api.loginByAccount(params).await()

    suspend fun getLoginCode(params: MutableMap<String, Any>) = api.getLoginCode(params).await()

    suspend fun loginByCode(params: MutableMap<String, Any>) = api.loginByCode(params).await()

    suspend fun refreshToken(params: MutableMap<String, Any>) = api.refreshToken(params).await()

    suspend fun getUserInfo() = api.getUserInfo().await()

    suspend fun getHomeData() = api.getHomeData().await()

}