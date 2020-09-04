package com.witaction.campusdefender.ui.main.my.account

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.witaction.campusdefender.ui.*
import com.witaction.common.extension.launch

class AccountSetViewModel : ViewModel() {
    val personInfo by lazy { MutableLiveData<User>(ServiceLocalReponsitory.getUser()) }
    val uploadAvatorResp by lazy { MutableLiveData<BResp<Avatar>>() }

    fun uploadAvator(content: String) {
        launch {
            val req = BRequest()
            req.addParams("Content", content)
            uploadAvatorResp.value = ServiceReponsitory.uploadAvatar(req)
        }
    }
}