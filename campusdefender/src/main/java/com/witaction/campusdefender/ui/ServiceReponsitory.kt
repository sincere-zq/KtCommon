package com.witaction.campusdefender.ui

import com.witaction.common.base.ServiceCreator
import com.witaction.common.base.await

object ServiceReponsitory {
    private lateinit var api: ServiceApi
    fun initApi(url: String) {
        api = ServiceCreator.getInstance().create(url)
    }

    suspend fun loginByAccount(request: BRequest) = api.loginByAccount(request).await()

    suspend fun accountPhoneValidate(request: BRequest) = api.accountPhoneValidate(request).await()

    suspend fun loginByCode(request: BRequest) = api.loginByCode(request).await()
}