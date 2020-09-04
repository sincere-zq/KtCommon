package com.witaction.campusdefender.ui.main.home

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.witaction.campusdefender.R
import com.witaction.campusdefender.ui.BusPlan
import com.witaction.common.utils.GlobalUtil

/**
 * 校车线路Adapter
 */
class BusPlanAdapter(lineList: MutableList<BusPlan>) :
    BaseQuickAdapter<BusPlan, BaseViewHolder>(R.layout.item_bus_plan, lineList) {
    override fun convert(holder: BaseViewHolder, item: BusPlan) {
        holder.setText(R.id.tv_line_name, item.lineName)
            .setText(
                R.id.tv_line_type,
                if (item.planType == 1) GlobalUtil.getString(R.string.on_school) else GlobalUtil.getString(
                    R.string.off_school
                )
            )
            .setBackgroundResource(
                R.id.tv_line_type,
                if (item.planType == 1) R.drawable.shape_tag_on_school else R.drawable.shape_tag_off_school
            ).setTextColorRes(
                R.id.tv_line_type,
                if (item.planType == 1) R.color.orange else R.color.blue
            ).setText(
                R.id.tv_line_status, when (item.planTaskStatus) {
                    1 -> R.string.working
                    2 -> R.string.finished
                    3 -> R.string.not_start
                    else -> R.string.stop_work
                }
            )
    }

}