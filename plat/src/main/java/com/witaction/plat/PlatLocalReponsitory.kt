package com.witaction.plat

import com.google.gson.Gson
import com.witaction.common.extension.edit
import com.witaction.common.extension.getObject
import com.witaction.common.utils.GlobalUtil

/**
 * 本地存储
 */
object PlatLocalReponsitory {
    private const val CITY = "CITY"
    private const val PLAT = "PLAT"
    private val gson = Gson()
    /**
     * 保存选择的城市到本地
     */
    fun saveCity(city: City) {
        GlobalUtil.sharedPreferences.edit {
            putString(CITY, gson.toJson(city))
        }
    }

    /**
     * 从本地获取选择的城市
     */
    fun getCity(): City? {
        val cityString = GlobalUtil.sharedPreferences.getString(CITY, "")
        return cityString?.let { gson.getObject<City>(it) }
    }

    /**
     * 保存选择的平台
     */
    fun savePlat(plat: Plat) {
        GlobalUtil.sharedPreferences.edit {
            putString(PLAT, gson.toJson(plat))
        }
    }

    /**
     * 获取选择的平台
     */
    fun getPlat(): Plat? {
        val platString = GlobalUtil.sharedPreferences.getString(PLAT, "")
        return platString?.let { gson.getObject<Plat>(it) }
    }
}