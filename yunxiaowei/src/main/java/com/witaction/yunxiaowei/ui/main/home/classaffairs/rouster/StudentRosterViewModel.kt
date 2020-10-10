package com.witaction.yunxiaowei.ui.main.home.classaffairs.rouster

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.witaction.common.extension.launch
import com.witaction.yunxiaowei.BResp
import com.witaction.yunxiaowei.ClassBean
import com.witaction.yunxiaowei.StudentRoster
import com.witaction.yunxiaowei.framwork.ServerRepository

class StudentRosterViewModel : ViewModel() {
    val classInfo by lazy { MutableLiveData<ClassBean>() }
    val studentRoster by lazy { MutableLiveData<BResp<StudentRoster>>() }

    fun getStudentRoster() {
        launch {
            classInfo.value?.run {
                studentRoster.value = ServerRepository.getStudentRoster(mutableMapOf("ClassId" to id))
            }
        }
    }
}