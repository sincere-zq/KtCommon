package com.witaction.campusdefender.ui.main.home

import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.witaction.campusdefender.R
import com.witaction.campusdefender.ui.ScheduleMultiItem

/**
 * 首页今天日程
 */
class HomeScheduleItem : BaseItemProvider<MultiItemEntity>() {
    override val itemViewType: Int
        get() = HomeAdapter.TYPE_SCHEDULE
    override val layoutId: Int
        get() = R.layout.item_home_schedule

    override fun convert(helper: BaseViewHolder, item: MultiItemEntity) {
        helper.setText(R.id.tv_title, R.string.today_schedule)
        val recyclerview = helper.getView<RecyclerView>(R.id.recyclerview)
        recyclerview.adapter = BusPlanAdapter((item as ScheduleMultiItem).transList)
    }
}