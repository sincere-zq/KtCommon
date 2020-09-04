package com.witaction.plat.ui.plat

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.witaction.plat.Plat
import com.witaction.plat.R

class PlatAdapter : BaseQuickAdapter<Plat, BaseViewHolder>(R.layout.item_plat) {
    override fun convert(holder: BaseViewHolder, item: Plat) {
        holder.setText(R.id.tv_plat_name, item.name)
            .setText(R.id.tv_plat_addr, item.address)
    }
}