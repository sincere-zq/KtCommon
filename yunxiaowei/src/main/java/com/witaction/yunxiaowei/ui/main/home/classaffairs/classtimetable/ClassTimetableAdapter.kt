package com.witaction.yunxiaowei.ui.main.home.classaffairs.classtimetable

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.witaction.common.utils.GlobalUtil
import com.witaction.yunxiaowei.CourseSection
import com.witaction.yunxiaowei.R

class ClassTimetableAdapter : BaseQuickAdapter<CourseSection, BaseViewHolder>(R.layout.item_course_section) {
    override fun convert(holder: BaseViewHolder, item: CourseSection) {
        holder.setText(R.id.tv_section, (item.section + 1).toString())
        val recyclerView = holder.getView<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = GridLayoutManager(context, 7)
        recyclerView.adapter = CourseNameAdapter(item.courseList)
        holder.itemView.setBackgroundColor(if (holder.adapterPosition % 2 == 0) GlobalUtil.getColor(R.color.circle_img_border) else GlobalUtil.getColor(R.color.white))
    }
}