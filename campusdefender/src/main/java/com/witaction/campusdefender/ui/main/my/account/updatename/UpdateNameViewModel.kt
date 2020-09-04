package com.witaction.campusdefender.ui.main.my.account.updatename

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.witaction.campusdefender.ui.*
import com.witaction.common.extension.launch

class UpdateNameViewModel : ViewModel() {
    val user by lazy { MutableLiveData<User>(ServiceLocalReponsitory.getUser()) }

    val updateNameResult by lazy { MutableLiveData<BResp<Any>>() }

    fun updateName(name: String) {
        launch {
            val req = BRequest()
            req.addParams("Name", name)
            updateNameResult.value = ServiceReponsitory.updateName(req)
        }
    }
}