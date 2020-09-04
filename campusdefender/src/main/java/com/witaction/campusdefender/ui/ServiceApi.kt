package com.witaction.campusdefender.ui

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ServiceApi {
    companion object {
        //账号密码登录
        const val URL_LOGIN_BY_PWD = "login/LoginByPwd/"
        //账号相关的手机号验证
        const val URL_ACCOUNT_PHONE_VALIDATE = "message/send/"
        //手机验证码登录
        const val URL_LOGIN_BY_CODE = "login/submit/"
        //获取首页顶部banner
        const val URL_GET_HOME_TOP_BANNER = "Ad/FocusNew/"
        //获取老师功能菜单
        const val URL_GET_TEACHER_SSOS_INFO = "login/GetParamBySsos/"
        //获取家长功能菜单
        const val URL_GET_PARENT_MENU = "Child/ParentMenu/"
        //获取事务提醒
        const val URL_GET_USER_MESSAGE = "UserMessage/GetUserMessage/"
        //根据日期查询线路
        const val URL_GET_PLAN_SCHOOL_BUS_LINE_BY_DATE_NEW = "BusPlanTask/NewPlanList/"
        //保存自定义菜单
        const val URL_UPDATE_CUSTOM_MODULE = "login/WriteCustomModuleSsos/"
        //上传头像
        const val URL_SEND_PARENTS_AVATAR = "User/UploadAvatar/"
        //修改名称
        const val URL_ALTER_NAME = "User/UpdateName/"
        //修改密码
        const val URL_ALTER_PWD = "User/UpdatePwd/"
    }

    @POST(URL_LOGIN_BY_PWD)
    fun loginByAccount(@Body request: BRequest): Call<BResp<User>>

    @POST(URL_ACCOUNT_PHONE_VALIDATE)
    fun accountPhoneValidate(@Body request: BRequest): Call<BResp<Any>>

    @POST(URL_LOGIN_BY_CODE)
    fun loginByCode(@Body request: BRequest): Call<BResp<User>>

    @POST(URL_GET_HOME_TOP_BANNER)
    fun getHomeTopBanner(@Body request: BRequest): Call<BResp<HomeBannerResult>>

    @POST(URL_GET_TEACHER_SSOS_INFO)
    fun getTeacherMenu(@Body request: BRequest): Call<BResp<TeacherMenuResult>>

    @POST(URL_GET_PARENT_MENU)
    fun getParentMenu(@Body request: BRequest): Call<BResp<ParentMenuResult>>

    @POST(URL_GET_USER_MESSAGE)
    fun getUserMsg(@Body request: BRequest): Call<BResp<TransAlert>>

    @POST(URL_GET_PLAN_SCHOOL_BUS_LINE_BY_DATE_NEW)
    fun getTeacherTodaySchedule(@Body request: BRequest): Call<BResp<BusPlan>>

    @POST(URL_UPDATE_CUSTOM_MODULE)
    fun saveCustomMenus(@Body request: BRequest): Call<BResp<Any>>

    @POST(URL_SEND_PARENTS_AVATAR)
    fun uploadAvator(@Body request: BRequest): Call<BResp<Avatar>>

    @POST(URL_ALTER_NAME)
    fun updateName(@Body request: BRequest): Call<BResp<Any>>

    @POST(URL_ALTER_PWD)
    fun updatePwd(@Body request: BRequest): Call<BResp<Any>>
}