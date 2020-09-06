package com.witaction.yunxiaowei.ui.main.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.witaction.common.extension.launch
import com.witaction.yunxiaowei.BResp
import com.witaction.yunxiaowei.HomeDataResult
import com.witaction.yunxiaowei.User
import com.witaction.yunxiaowei.framwork.LocalRepository
import com.witaction.yunxiaowei.framwork.ServerRepository

class HomeViewModel : ViewModel() {
    val homeRealResultData by lazy { MutableLiveData<BResp<HomeDataResult>>() }

    val userInfo by lazy { MutableLiveData<User>(LocalRepository.getUserInfo()) }

    fun getHomeData() {
        launch {
            homeRealResultData.value = ServerRepository.getHomeData()
        }
    }
}