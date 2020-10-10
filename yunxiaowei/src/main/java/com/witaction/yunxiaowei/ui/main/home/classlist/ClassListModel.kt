package com.witaction.yunxiaowei.ui.main.home.classlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.witaction.common.extension.launch
import com.witaction.yunxiaowei.BResp
import com.witaction.yunxiaowei.ClassBean
import com.witaction.yunxiaowei.framwork.ServerRepository
import kotlin.properties.Delegates

class ClassListModel : ViewModel() {
    val classList by lazy { MutableLiveData<BResp<ClassBean>>() }
    var authFilter by Delegates.notNull<Int>()

    fun getClassList() {
        launch {
            classList.value =
                ServerRepository.getClassList(mutableMapOf("AuthFilter" to authFilter))
        }
    }
}