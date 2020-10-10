package com.witaction.yunxiaowei.ui.main.home.classaffairs.addstatus

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.witaction.common.extension.launch
import com.witaction.yunxiaowei.BResp
import com.witaction.yunxiaowei.StudentRoster
import com.witaction.yunxiaowei.framwork.ServerRepository

class AddStuStatusViewModel : ViewModel() {
    val studentInfo by lazy { MutableLiveData<StudentRoster>() }
    val updateStuStatus by lazy { MutableLiveData<BResp<Any>>() }

    fun updateStuStatus(identityNo: String) {
        launch {
            studentInfo.value?.run {
                updateStuStatus.value = ServerRepository.updateStuStatus(mutableMapOf("StudentId" to id, "IdentityNo" to identityNo))
            }
        }
    }
}