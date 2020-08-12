package com.witaction.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.witaction.common.base.BaseResp
import com.witaction.common.base.City
import com.witaction.common.base.launch
import com.witaction.common.utils.e
import kotlinx.coroutines.Job

class MViewModel : ViewModel() {
    val cityListResult = MutableLiveData<BaseResp<MutableList<City>>>()
    lateinit var job: Job

    fun getCityList() {
        job = launch {
            val value = LoginRep.getCityList()
            e(value.toString())
            cityListResult.value = value
        }
    }
}