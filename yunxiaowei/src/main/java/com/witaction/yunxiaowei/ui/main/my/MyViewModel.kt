package com.witaction.yunxiaowei.ui.main.my

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.witaction.yunxiaowei.User
import com.witaction.yunxiaowei.framwork.LocalRepository

class MyViewModel : ViewModel() {
    val userInfo by lazy { MutableLiveData<User>(LocalRepository.getUserInfo()) }
}