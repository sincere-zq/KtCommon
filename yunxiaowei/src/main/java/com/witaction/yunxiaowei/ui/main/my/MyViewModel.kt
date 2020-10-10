package com.witaction.yunxiaowei.ui.main.my

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.witaction.common.extension.launch
import com.witaction.yunxiaowei.BResp
import com.witaction.yunxiaowei.User
import com.witaction.yunxiaowei.framwork.LocalRepository
import com.witaction.yunxiaowei.framwork.ServerRepository

class MyViewModel : ViewModel() {
    val userInfo by lazy { MutableLiveData<User>(LocalRepository.getUserInfo()) }

    val switchIdentityResult by lazy { MutableLiveData<BResp<Any>>() }

    fun switchIdentity() {
        launch {
            switchIdentityResult.value = ServerRepository.switchIdentity()
        }
    }
}