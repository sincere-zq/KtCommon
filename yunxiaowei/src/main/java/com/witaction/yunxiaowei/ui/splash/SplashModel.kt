package com.witaction.yunxiaowei.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.witaction.common.extension.launch
import com.witaction.plat.PlatLocalReponsitory
import com.witaction.yunxiaowei.BResp
import com.witaction.yunxiaowei.LoginResult
import com.witaction.yunxiaowei.framwork.LocalRepository
import com.witaction.yunxiaowei.framwork.ServerRepository

class SplashModel : ViewModel() {
    val plat by lazy { PlatLocalReponsitory.getPlat() }

    val localLoginResult by lazy { LocalRepository.getLoginResult() }

    val loginResult = MutableLiveData<BResp<LoginResult>>()

    fun refreshToken() {
        launch {
            localLoginResult?.let {
                val params = mutableMapOf<String, Any>(
                    Pair("AccessToken", it.accessToken),
                    Pair("RefreshToken", it.refreshToken)
                )
                val result = ServerRepository.refreshToken(params)
                if (result.isSuccess()) {
                    getUserInfo(result)
                }
                loginResult.value = result
            }
        }
    }

    suspend fun getUserInfo(result: BResp<LoginResult>) {
        if (LocalRepository.getUserInfo() == null) {
            val userInfo = ServerRepository.getUserInfo()
            if (userInfo.isSuccess() && userInfo.getSimpleData() != null) {
                LocalRepository.saveUserInfo(userInfo.getSimpleData()!!)
            } else {
                result.isSuccess = 0
            }
        }
    }

}