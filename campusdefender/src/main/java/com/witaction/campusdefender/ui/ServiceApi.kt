package com.witaction.campusdefender.ui

import com.witaction.common.base.BResp
import com.witaction.common.model.User
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
    }

    @POST(URL_LOGIN_BY_PWD)
    fun loginByAccount(@Body request: BRequest): Call<BResp<User>>

    @POST(URL_ACCOUNT_PHONE_VALIDATE)
    fun accountPhoneValidate(@Body request: BRequest): Call<BResp<Any>>

    @POST(URL_LOGIN_BY_CODE)
    fun loginByCode(@Body request: BRequest): Call<BResp<User>>
}