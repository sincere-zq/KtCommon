package com.witaction.yunxiaowei.framwork

import com.witaction.yunxiaowei.BResp
import com.witaction.yunxiaowei.HomeDataResult
import com.witaction.yunxiaowei.LoginResult
import com.witaction.yunxiaowei.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ServerApi {
    companion object {
        //账号密码登录
        const val URL_LOGIN_BY_ACCOUNT = "Login/LoginByPwd"
        //获取登录验证码
        const val URL_GET_LOGIN_CODE = "Login/GetVerCode_Login"
        //验证码登录
        const val URL_LOGIN_BY_CODE = "Login/LoginByVerCode"
        //刷新token
        const val URL_REFRESH_TOKEN = "Login/UpdateAccessToken"
        //初始密码
        const val URL_INIT_PWD = "Login/SaveInitPassword"
        //获取用户信息
        const val URL_GET_USER_INFO = "MyCenter/GetUserInfo"
        //获取首页数据
        const val URL_GET_HOME_DATAS = "HomePage/GetHomePageInfo"

    }

    @POST(URL_LOGIN_BY_ACCOUNT)
    fun loginByAccount(@Body params: MutableMap<String, Any>): Call<BResp<LoginResult>>

    @POST(URL_GET_LOGIN_CODE)
    fun getLoginCode(@Body params: MutableMap<String, Any>): Call<BResp<Any>>

    @POST(URL_LOGIN_BY_CODE)
    fun loginByCode(@Body params: MutableMap<String, Any>): Call<BResp<LoginResult>>

    @POST(URL_REFRESH_TOKEN)
    fun refreshToken(@Body params: MutableMap<String, Any>): Call<BResp<LoginResult>>

    @POST(URL_GET_USER_INFO)
    fun getUserInfo(): Call<BResp<User>>

    @POST(URL_GET_HOME_DATAS)
    fun getHomeData(): Call<BResp<HomeDataResult>>
}