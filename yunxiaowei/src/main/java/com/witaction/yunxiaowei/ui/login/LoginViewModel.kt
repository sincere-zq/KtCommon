package com.witaction.yunxiaowei.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.witaction.common.extension.launch
import com.witaction.plat.Plat
import com.witaction.yunxiaowei.BResp
import com.witaction.yunxiaowei.LoginResult
import com.witaction.yunxiaowei.framwork.LocalRepository
import com.witaction.yunxiaowei.framwork.ServerRepository

class LoginViewModel : ViewModel() {
    private val clientType = 1
    val loginType = MutableLiveData(LoginActivity.LOGIN_BY_ACCOUNT)
    val plat = MutableLiveData<Plat>()
    val user = MutableLiveData<BResp<LoginResult>>()
    val phoneValidateResult = MutableLiveData<BResp<Any>>()
    fun loginByAccount(account: String, pwd: String) {
        launch {
            plat.value?.let {
                val request = mutableMapOf(
                    "Account" to account, "PassWord" to pwd,
                    "ClientType" to clientType, "SchoolId" to it.sysID
                )
                val result = ServerRepository.loginByAccount(request)
                getUserInfo(ServerRepository.loginByAccount(request))
                user.value = result
            }
        }
    }

    fun accountPhoneValidate(phone: String) {
        launch {
            plat.value?.let {
                val request = mutableMapOf<String, Any>(
                    "MobilePhone" to phone,
                    "SchoolId" to it.sysID
                )
                phoneValidateResult.value = ServerRepository.getLoginCode(request)
            }
        }
    }

    fun loginByCode(phone: String, code: String) {
        launch {
            plat.value?.let {
                val request = mutableMapOf(
                    "MobilePhone" to phone, "VerCode" to code,
                    "ClientType" to clientType, "SchoolId" to it.sysID
                )
                val result = ServerRepository.loginByCode(request)
                getUserInfo(result)
                user.value = result
            }
        }
    }

    private suspend fun getUserInfo(result: BResp<LoginResult>) {
        if (result.isSuccess()) {
            result.getSimpleData()?.let {
                LocalRepository.saveLoginResult(it)
                val userInfo = ServerRepository.getUserInfo()
                if (userInfo.isSuccess() && userInfo.getSimpleData() != null) {
                    LocalRepository.saveUserInfo(userInfo.getSimpleData()!!)
                } else {
                    result.isSuccess = 0
                    result.msg = userInfo.msg
                }
            }
        }
    }

}