package com.witaction.campusdefender.ui.main.home

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.witaction.campusdefender.AppConfig
import com.witaction.campusdefender.R
import com.witaction.campusdefender.ui.TeacherMenuMultiItem

/**
 * 首页老师菜单
 */
class HomeTeacherMenuItem : BaseItemProvider<MultiItemEntity>() {
    override val itemViewType: Int
        get() = HomeAdapter.TYPE_TEACHER_MENU
    override val layoutId: Int
        get() = R.layout.item_home_menu

    override fun convert(helper: BaseViewHolder, item: MultiItemEntity) {
        val recyclerView = helper.getView<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = GridLayoutManager(context, AppConfig.HOME_MENU_NUM)
        recyclerView.adapter =
            TeacherMenuAdapter((item as TeacherMenuMultiItem).menuSectionList)
    }
}