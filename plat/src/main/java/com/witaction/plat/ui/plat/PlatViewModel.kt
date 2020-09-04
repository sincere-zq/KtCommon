package com.witaction.plat.ui.plat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.witaction.common.base.BaseResp
import com.witaction.common.extension.launch
import com.witaction.plat.City
import com.witaction.plat.Plat
import com.witaction.plat.PlatRepository

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