package com.witaction.campusdefender.ui.login.city

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.witaction.campusdefender.R
import com.witaction.common.model.City

class CityAdapter : BaseQuickAdapter<City, BaseViewHolder>(R.layout.item_city) {
    override fun convert(holder: BaseViewHolder, item: City) {
        holder.setText(R.id.tv_city, item.cityName)
    }
}