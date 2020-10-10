package com.witaction.yunxiaowei.ui.main.home

import android.app.Activity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.witaction.common.utils.GlobalUtil
import com.witaction.yunxiaowei.FunctionMenu
import com.witaction.yunxiaowei.R
import com.witaction.yunxiaowei.framwork.Router

class HomeFuncMenuItemAdapter : BaseQuickAdapter<FunctionMenu, BaseViewHolder>(R.layout.item_menu) {
    private val defaultIconName = "bp-icon-one"
    override fun convert(holder: BaseViewHolder, item: FunctionMenu) {
        holder.setText(R.id.tv_menu, item.name)
        if (item.name == "" || item.name == defaultIconName) {
            holder.setImageResource(R.id.img_menu, R.mipmap.icon_home_placeholder)
        } else {
            val icon = GlobalUtil.getResourceByName(context, item.icon)
            holder.setImageResource(
                R.id.img_menu,
                if (icon == 0) R.mipmap.icon_home_placeholder else icon
            )
        }
        holder.itemView.setOnClickListener { Router.open(context as Activity, item) }
    }
}