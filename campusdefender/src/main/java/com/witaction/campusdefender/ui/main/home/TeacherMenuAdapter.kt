package com.witaction.campusdefender.ui.main.home

import android.app.Activity
import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.witaction.campusdefender.R
import com.witaction.campusdefender.ui.main.home.editmenu.EditMenuActivity
import com.witaction.common.extension.open
import com.witaction.campusdefender.ui.TeacherMenuSection
import com.witaction.common.utils.GlobalUtil
import com.witaction.common.utils.toast

class TeacherMenuAdapter(teacherMenuList: MutableList<TeacherMenuSection>) :
    BaseSectionQuickAdapter<TeacherMenuSection, BaseViewHolder>(
        R.layout.item_menu_head,
        R.layout.item_menu,
        teacherMenuList
    ) {
    var isEdit = false
    private val default_icon_name = "bp-icon-one"
    override fun convert(holder: BaseViewHolder, item: TeacherMenuSection) {
        holder.setText(R.id.tv_menu, item.menu.name)
            .setVisible(R.id.img_edit, isEdit)
            .setImageResource(
                R.id.img_edit,
                if (item.menu.isAdd) R.mipmap.ic_delete_teacher_menu else R.mipmap.ic_add_teacher_menu
            )
        if (item.menu.name == "" || item.menu.name == default_icon_name) {
            holder.setImageResource(R.id.img_menu, R.mipmap.icon_home_placeholder)
        } else {
            val icon = GlobalUtil.getResourceByName(context, item.menu.icon)
            holder.setImageResource(
                R.id.img_menu,
                if (icon == 0) R.mipmap.icon_home_placeholder else icon
            )
        }
        holder.itemView.setOnClickListener {
            when (item.menu.name) {
                GlobalUtil.getString(R.string.more) -> (context as Activity).open<EditMenuActivity>()
                else -> toast(item.menu.name)
            }
        }
    }

    override fun convertHeader(helper: BaseViewHolder, item: TeacherMenuSection) {
        helper.setText(R.id.tv_title, item.header)
    }
}