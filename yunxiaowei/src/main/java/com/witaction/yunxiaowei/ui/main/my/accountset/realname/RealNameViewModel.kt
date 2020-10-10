package com.witaction.yunxiaowei.ui.main.my.accountset.realname

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.witaction.common.extension.launch
import com.witaction.yunxiaowei.BResp
import com.witaction.yunxiaowei.User
import com.witaction.yunxiaowei.framwork.LocalRepository
import com.witaction.yunxiaowei.framwork.ServerRepository

class RealNameViewModel : ViewModel() {
    val userInfo by lazy { MutableLiveData<User>(LocalRepository.getUserInfo()) }

    val saveRealNameResult by lazy { MutableLiveData<BResp<Any>>() }

    fun saveRealName(name: String) {
        launch {
            saveRealNameResult.value =
                ServerRepository.saveRealName(mutableMapOf("Name" to name))
        }
    }
}