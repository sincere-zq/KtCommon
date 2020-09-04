package com.witaction.campusdefender.ui.main.home.editmenu

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.DraggableModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.witaction.campusdefender.R
import com.witaction.campusdefender.ui.TeacherMenu
import com.witaction.common.utils.GlobalUtil
import com.witaction.common.utils.toast

class CustomMenuAdapter : BaseQuickAdapter<TeacherMenu, BaseViewHolder>(R.layout.item_menu),
    DraggableModule {
    var isEdit = false
    private val default_icon_name = "bp-icon-one"
    override fun convert(holder: BaseViewHolder, item: TeacherMenu) {
        holder.setText(R.id.tv_menu, item.name)
            .setVisible(R.id.img_edit, isEdit)
            .setImageResource(
                R.id.img_edit, R.mipmap.ic_delete_teacher_menu
            )
        if (item.name == "" || item.name == default_icon_name) {
            holder.setImageResource(R.id.img_menu, R.mipmap.icon_home_placeholder)
        } else {
            val icon = GlobalUtil.getResourceByName(context, item.icon)
            holder.setImageResource(
                R.id.img_menu,
                if (icon == 0) R.mipmap.icon_home_placeholder else icon
            )
        }
        holder.itemView.setOnClickListener {
            toast(item.name)
        }
    }
}