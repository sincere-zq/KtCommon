package com.witaction.yunxiaowei.ui.main.my.accountset.resetphone

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.witaction.common.extension.launch
import com.witaction.yunxiaowei.BResp
import com.witaction.yunxiaowei.framwork.ServerRepository

class ResetPhoneViewModel : ViewModel() {
    val sendCodeResult by lazy { MutableLiveData<BResp<Any>>() }

    val resetPhoneResult by lazy { MutableLiveData<BResp<Any>>() }

    fun sendCode(phone: String) {
        launch {
            sendCodeResult.value =
                ServerRepository.getResetPhoneCode(mutableMapOf("MobilePhone" to phone))
        }
    }

    fun resetPhone(phone: String, code: String) {
        launch {
            resetPhoneResult.value = ServerRepository.resetPhone(
                mutableMapOf(
                    "NewPhone" to phone,
                    "VerCode" to code
                )
            )
        }
    }
}