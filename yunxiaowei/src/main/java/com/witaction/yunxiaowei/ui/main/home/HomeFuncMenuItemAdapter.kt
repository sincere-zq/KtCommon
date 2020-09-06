package com.witaction.yunxiaowei.ui.main.home

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.witaction.common.utils.GlobalUtil
import com.witaction.yunxiaowei.FunctionMenu
import com.witaction.yunxiaowei.R

class HomeFuncMenuItemAdapter : BaseQuickAdapter<FunctionMenu, BaseViewHolder>(R.layout.item_menu) {
    private val default_icon_name = "bp-icon-one"
    override fun convert(holder: BaseViewHolder, item: FunctionMenu) {
        holder.setText(R.id.tv_menu, item.name)
        if (item.name == "" || item.name == default_icon_name) {
            holder.setImageResource(R.id.img_menu, R.mipmap.icon_home_placeholder)
        } else {
            val icon = GlobalUtil.getResourceByName(context, item.icon)
            holder.setImageResource(
                R.id.img_menu,
                if (icon == 0) R.mipmap.icon_home_placeholder else icon
            )
        }
    }
}