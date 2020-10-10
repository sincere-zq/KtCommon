package com.witaction.yunxiaowei.ui.main.my.accountset

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.witaction.common.extension.launch
import com.witaction.yunxiaowei.BResp
import com.witaction.yunxiaowei.UpdateAvatarResult
import com.witaction.yunxiaowei.User
import com.witaction.yunxiaowei.framwork.LocalRepository
import com.witaction.yunxiaowei.framwork.ServerRepository

class AccountSetViewMode : ViewModel() {

    val userInfo by lazy { MutableLiveData<User>(LocalRepository.getUserInfo()) }

    val updateAvatarResult by lazy { MutableLiveData<BResp<UpdateAvatarResult>>() }

    fun updateAvatar(content: String) {
        launch {
            updateAvatarResult.value =
                ServerRepository.updateAvatar(mutableMapOf("Content" to content))
        }
    }
}