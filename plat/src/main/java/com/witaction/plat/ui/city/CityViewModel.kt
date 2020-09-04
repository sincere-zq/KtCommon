package com.witaction.plat.ui.city

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.witaction.common.base.BaseResp
import com.witaction.common.extension.launch
import com.witaction.plat.City
import com.witaction.plat.PlatRepository

class CityViewModel : ViewModel() {
    val cityList = MutableLiveData<BaseResp<List<City>>>()

    fun getCityList() {
        launch {
            cityList.value = PlatRepository.getCityList()
        }
    }
}