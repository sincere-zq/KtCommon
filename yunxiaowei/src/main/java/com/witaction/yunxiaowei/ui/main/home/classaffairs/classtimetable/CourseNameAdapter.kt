package com.witaction.yunxiaowei.ui.main.home.classaffairs.classtimetable

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.witaction.yunxiaowei.R

class CourseNameAdapter(datas: MutableList<String>) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_course_name, datas) {
    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.tv_course_name, item)
    }
}