package com.witaction.yunxiaowei.ui.main.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.witaction.common.extension.launch
import com.witaction.yunxiaowei.BResp
import com.witaction.yunxiaowei.HomeDataResult
import com.witaction.yunxiaowei.framwork.ServerRepository

class HomeViewModel : ViewModel() {
    val homeDataResult by lazy { MutableLiveData<BResp<HomeDataResult>>() }

    fun getHomeData() {
        launch {
            homeDataResult.value = ServerRepository.getHomeData()
        }
    }
}