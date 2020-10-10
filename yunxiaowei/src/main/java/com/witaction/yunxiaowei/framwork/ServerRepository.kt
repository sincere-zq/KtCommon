package com.witaction.yunxiaowei.framwork

object ServerRepository {
    private lateinit var api: ServerApi
    fun initApi(url: String) {
        api = ServerCreator.getInstance().create("http://demo.sgurad.com:82/apiv1/")
    }

    suspend fun loginByAccount(params: MutableMap<String, Any>) = api.loginByAccount(params).await()

    suspend fun getLoginCode(params: MutableMap<String, Any>) = api.getLoginCode(params).await()

    suspend fun loginByCode(params: MutableMap<String, Any>) = api.loginByCode(params).await()

    suspend fun refreshToken(params: MutableMap<String, Any>) = api.refreshToken(params).await()

    suspend fun getUserInfo() = api.getUserInfo().await()

    suspend fun getHomeData() = api.getHomeData().await()

    suspend fun switchIdentity() = api.switchIdentity().await()

    suspend fun updateAvatar(params: MutableMap<String, Any>) = api.updateAvatar(params).await()

    suspend fun saveRealName(params: MutableMap<String, Any>) = api.saveRealName(params).await()

    suspend fun resetPwd(params: MutableMap<String, Any>) = api.resetPwd(params).await()

    suspend fun getResetPwdCode() = api.getResetPwdCode().await()

    suspend fun resetPhone(params: MutableMap<String, Any>) = api.resetPhone(params).await()

    suspend fun getResetPhoneCode(params: MutableMap<String, Any>) = api.getResetPhoneCode(params).await()

    suspend fun getPersonFaceList(params: MutableMap<String, Any>) = api.getPersonFaceList(params).await()

    suspend fun deleteFace(params: MutableMap<String, Any>) = api.deleteFace(params).await()

    suspend fun uploadFace(params: MutableMap<String, Any>) = api.uploadFace(params).await()

    suspend fun getClassList(params: MutableMap<String, Any>) = api.getClassList(params).await()

    suspend fun getStudentRoster(params: MutableMap<String, Any>) = api.getStudentRoster(params).await()

    suspend fun updateStuStatus(params: MutableMap<String, Any>) = api.updateStuStatus(params).await()

    suspend fun getClassTimetable(params: MutableMap<String, Any>) = api.getClassTimetable(params).await()

    suspend fun getStuTimetable(params: MutableMap<String, Any>) = api.getStuTimetable(params).await()

}