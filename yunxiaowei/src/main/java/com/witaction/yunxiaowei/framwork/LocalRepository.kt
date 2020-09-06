package com.witaction.yunxiaowei.framwork

import com.google.gson.Gson
import com.witaction.common.extension.edit
import com.witaction.common.extension.getObject
import com.witaction.common.utils.GlobalUtil
import com.witaction.yunxiaowei.LoginResult
import com.witaction.yunxiaowei.User

object LocalRepository {
    private val gson = Gson()
    private const val LOGIN_RESULT = "LOGIN_RESULT"
    private const val USER_INFO = "USER_INFO"

    /**
     * 保存登录结果
     */
    fun saveLoginResult(loginResult: LoginResult) {
        GlobalUtil.sharedPreferences.edit { putString(LOGIN_RESULT, gson.toJson(loginResult)) }
    }

    /**
     * 获取登录结果
     */
    fun getLoginResult(): LoginResult? {
        val string = GlobalUtil.sharedPreferences.getString(LOGIN_RESULT, "")
        return string?.let { gson.getObject(it) }
    }

    /**
     * 保存用户信息
     */
    fun saveUserInfo(user: User) {
        GlobalUtil.sharedPreferences.edit { putString(USER_INFO, gson.toJson(user)) }
    }

    /**
     * 获取用户信息
     */
    fun getUserInfo(): User? {
        val string = GlobalUtil.sharedPreferences.getString(USER_INFO, "")
        return string?.let { gson.getObject(it) }
    }

    /**
     * 退出登录
     */
    fun loginOut() {
        GlobalUtil.sharedPreferences.edit {
            remove(LOGIN_RESULT)
            remove(USER_INFO)
        }
    }
}