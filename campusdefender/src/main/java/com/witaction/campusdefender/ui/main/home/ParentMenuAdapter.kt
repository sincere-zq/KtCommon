package com.witaction.campusdefender.ui.main.home

import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.witaction.campusdefender.R
import com.witaction.campusdefender.ui.ParentMenuSection
import com.witaction.common.utils.GlobalUtil

class ParentMenuAdapter(parentMenuList: MutableList<ParentMenuSection>) :
    BaseSectionQuickAdapter<ParentMenuSection, BaseViewHolder>(
        R.layout.item_menu_head,
        R.layout.item_menu,
        parentMenuList
    ) {
    private val default_icon_name = "bp-icon-one"
    override fun convert(holder: BaseViewHolder, item: ParentMenuSection) {
        holder.setText(R.id.tv_menu, item.menu.name)
        if (item.menu.name == "" || item.menu.name == default_icon_name) {
            holder.setImageResource(R.id.img_menu, R.mipmap.icon_home_placeholder)
        } else {
            val icon = GlobalUtil.getResourceByName(context, item.menu.icon)
            holder.setImageResource(
                R.id.img_menu,
                if (icon == 0) R.mipmap.icon_home_placeholder else icon
            )
        }
    }

    override fun convertHeader(helper: BaseViewHolder, item: ParentMenuSection) {
        helper.setText(R.id.tv_title, item.header)

    }
}