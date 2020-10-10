package com.witaction.yunxiaowei.ui.main.my.accountset.resetpwd

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.witaction.common.extension.launch
import com.witaction.yunxiaowei.BResp
import com.witaction.yunxiaowei.framwork.ServerRepository

class ResetPwdViewModel : ViewModel() {
    val sendCodeResult by lazy { MutableLiveData<BResp<Any>>() }

    val resetPwdResult by lazy { MutableLiveData<BResp<Any>>() }

    fun sendCode() {
        launch {
            sendCodeResult.value = ServerRepository.getResetPwdCode()
        }
    }

    fun resetPwd(pwd: String, code: String) {
        launch {
            resetPwdResult.value = ServerRepository.resetPwd(
                mutableMapOf(
                    "Password" to pwd,
                    "VerCode" to code
                )
            )
        }
    }

}