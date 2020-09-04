package com.witaction.campusdefender.ui.main.home

import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.witaction.campusdefender.R
import com.witaction.campusdefender.ui.TransAlertMultiItem

/**
 * 首页事务提醒
 */
class HomeTransItem : BaseItemProvider<MultiItemEntity>() {
    override val itemViewType: Int
        get() = HomeAdapter.TYPE_TRANS
    override val layoutId: Int
        get() = R.layout.item_home_schedule

    override fun convert(helper: BaseViewHolder, item: MultiItemEntity) {
        helper.setText(R.id.tv_title, R.string.trans_alert)
        val recyclerView = helper.getView<RecyclerView>(R.id.recyclerview)
        recyclerView.adapter = TransAlertAdapter((item as TransAlertMultiItem).transList)
    }
}