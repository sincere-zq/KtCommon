package com.witaction.campusdefender.ui.login.plat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.witaction.common.model.City
import com.witaction.common.model.Plat
import com.witaction.campusdefender.ui.login.PlatRepository
import com.witaction.common.base.BaseResp
import com.witaction.common.extension.launch

class PlatViewModel : ViewModel() {
    val city = MutableLiveData<City>()
    val platList = MutableLiveData<BaseResp<List<Plat>>>()
    fun getPlatList(cityCode: String) {
        launch {
            val params = mutableMapOf<String, Any>(
                Pair("Lon", ""), Pair("Lat", ""), Pair("keyword", ""),
                Pair("cityCode", cityCode)
            )
            platList.value = PlatRepository.getPlatList(params)
        }
    }
}