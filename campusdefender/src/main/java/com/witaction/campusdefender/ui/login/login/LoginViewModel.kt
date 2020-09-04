package com.witaction.campusdefender.ui.login.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.witaction.campusdefender.AppConfig
import com.witaction.campusdefender.ui.BRequest
import com.witaction.campusdefender.ui.BResp
import com.witaction.campusdefender.ui.ServiceReponsitory
import com.witaction.campusdefender.ui.User
import com.witaction.common.extension.launch
import com.witaction.common.utils.DESUtil
import com.witaction.plat.Plat

class LoginViewModel : ViewModel() {
    val loginType = MutableLiveData(LoginActivity.LOGIN_BY_ACCOUNT)
    val plat = MutableLiveData<Plat>()
    val user = MutableLiveData<BResp<User>>()
    val phoneValidateResult = MutableLiveData<BResp<Any>>()
    fun loginByAccount(account: String, pwd: String) {
        launch {
            val request = BRequest()
            request.addParams("Phone", account)
            request.addParams("Pwd", pwd)
            user.value = ServiceReponsitory.loginByAccount(request)
        }
    }

    fun accountPhoneValidate(phone: String) {
        launch {
            val request = BRequest()
            request.addParams(
                "Phone",
                DESUtil.encryptDES(AppConfig.DES_KEY, AppConfig.DES_IV, phone)
            )
            phoneValidateResult.value = ServiceReponsitory.accountPhoneValidate(request)
        }
    }

    fun loginByCode(phone: String, code: String) {
        launch {
            val request = BRequest()
            request.addParams("Phone", phone)
            request.addParams("Code", code)
            user.value = ServiceReponsitory.loginByCode(request)
        }
    }
}