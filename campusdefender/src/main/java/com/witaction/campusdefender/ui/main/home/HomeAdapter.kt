package com.witaction.campusdefender.ui.main.home

import com.chad.library.adapter.base.BaseProviderMultiAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * 首页适配器
 */
class HomeAdapter : BaseProviderMultiAdapter<MultiItemEntity>() {
    companion object {
        const val TYPE_BANNER = 0x110
        const val TYPE_STUDENT_MENU = 0x111
        const val TYPE_TEACHER_MENU = 0x112
        const val TYPE_PARENT_MENU = 0x113
        const val TYPE_TRANS = 0x114
        const val TYPE_SCHEDULE = 0x115
    }

    init {
        addItemProvider(HomeBannerItem())
        addItemProvider(HomeTeacherMenuItem())
        addItemProvider(HomeParentMenuItem())
        addItemProvider(HomeTransItem())
        addItemProvider(HomeScheduleItem())
    }

    override fun getItemType(data: List<MultiItemEntity>, position: Int): Int =
        data[position].itemType
}