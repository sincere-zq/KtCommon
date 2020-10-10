package com.witaction.yunxiaowei.ui.main.home.classlist

import android.text.TextUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.witaction.common.utils.GlobalUtil
import com.witaction.yunxiaowei.ClassBean
import com.witaction.yunxiaowei.R

class ClassAdapter : BaseQuickAdapter<ClassBean, BaseViewHolder>(R.layout.item_class) {
    private val colorIds = GlobalUtil.getIntArray(R.array.class_colors)
    override fun convert(holder: BaseViewHolder, item: ClassBean) {
        holder.setText(R.id.tv_grade, item.grade)
            .setBackgroundColor(R.id.tv_grade, colorIds[holder.adapterPosition % colorIds.size])
            .setText(R.id.tv_class_name, item.name)
            .setText(R.id.tv_class_teacher, "班主任：${item.classTeacherName}")
            .setEnabled(R.id.tv_is_class_teacher, item.isClassTeacher)
            .setText(R.id.tv_teacher_phone, item.classTeacherAccount)
            .setGone(R.id.tv_teacher_phone, TextUtils.isEmpty(item.classTeacherAccount))
    }
}