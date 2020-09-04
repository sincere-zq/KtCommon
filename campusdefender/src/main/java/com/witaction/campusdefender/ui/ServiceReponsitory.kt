package com.witaction.campusdefender.ui

import com.witaction.common.base.ServiceCreator

object ServiceReponsitory {
    private lateinit var api: ServiceApi
    fun initApi(url: String) {
        api = ServiceCreator.getInstance().create(url)
    }

    suspend fun loginByAccount(request: BRequest) = api.loginByAccount(request).await()

    suspend fun accountPhoneValidate(request: BRequest) = api.accountPhoneValidate(request).await()

    suspend fun loginByCode(request: BRequest) = api.loginByCode(request).await()

    suspend fun getHomeTopBanner(request: BRequest) = api.getHomeTopBanner(request).await()

    suspend fun getTeacherMenu(request: BRequest) = api.getTeacherMenu(request).await()

    suspend fun getParentMenu(request: BRequest) = api.getParentMenu(request).await()

    suspend fun getUserMsg(request: BRequest) = api.getUserMsg(request).await()

    suspend fun getTeacherTodaySchedule(request: BRequest) =
        api.getTeacherTodaySchedule(request).await()

    suspend fun saveCustomMenu(request: BRequest) = api.saveCustomMenus(request).await()

    suspend fun uploadAvatar(request: BRequest) = api.uploadAvator(request).await()

    suspend fun updateName(request: BRequest) = api.updateName(request).await()

    suspend fun updatePwd(request: BRequest) = api.updatePwd(request).await()


}