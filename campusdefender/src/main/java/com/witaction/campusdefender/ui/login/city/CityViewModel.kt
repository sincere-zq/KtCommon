package com.witaction.campusdefender.ui.login.city

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.witaction.common.model.City
import com.witaction.campusdefender.ui.login.PlatRepository
import com.witaction.common.base.BaseResp
import com.witaction.common.extension.launch

class CityViewModel : ViewModel() {
    val cityList = MutableLiveData<BaseResp<List<City>>>()

    fun getCityList() {
        launch {
            cityList.value = PlatRepository.getCityList()
        }
    }
}