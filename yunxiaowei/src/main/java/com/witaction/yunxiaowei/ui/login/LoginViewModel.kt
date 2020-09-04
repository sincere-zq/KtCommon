package com.witaction.yunxiaowei.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.witaction.common.extension.launch
import com.witaction.plat.Plat
import com.witaction.yunxiaowei.BResp
import com.witaction.yunxiaowei.LoginResult
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
                    Pair("Account", account),
                    Pair("PassWord", pwd),
                    Pair("ClientType", clientType),
                    Pair("SchoolId", it.sysID)
                )
                user.value = ServerRepository.loginByAccount(request)
            }
        }
    }

    fun accountPhoneValidate(phone: String) {
        launch {
            plat.value?.let {
                val request = mutableMapOf<String, Any>(
                    Pair("MobilePhone", phone),
                    Pair("SchoolId", it.sysID)
                )
                phoneValidateResult.value = ServerRepository.getLoginCode(request)
            }
        }
    }

    fun loginByCode(phone: String, code: String) {
        launch {
            plat.value?.let {
                val request = mutableMapOf(
                    Pair("MobilePhone", phone),
                    Pair("VerCode", code),
                    Pair("ClientType", clientType),
                    Pair("SchoolId", it.sysID)
                )
                user.value = ServerRepository.loginByCode(request)
            }
        }
    }
}