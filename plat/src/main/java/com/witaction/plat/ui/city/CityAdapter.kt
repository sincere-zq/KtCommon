package com.witaction.plat.ui.city

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.witaction.plat.City
import com.witaction.plat.R

class CityAdapter : BaseQuickAdapter<City, BaseViewHolder>(R.layout.item_city) {
    override fun convert(holder: BaseViewHolder, item: City) {
        holder.setText(R.id.tv_city, item.cityName)
    }
}