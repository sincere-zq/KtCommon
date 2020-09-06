package com.witaction.yunxiaowei.ui.main.home

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.witaction.yunxiaowei.HomeDataMenu
import com.witaction.yunxiaowei.R
import com.witaction.yunxiaowei.framwork.AppConfig

class HomeFuncMenuItem : BaseItemProvider<MultiItemEntity>() {
    override val itemViewType: Int
        get() = HomeAdapter.HOME_FUNC_MENU
    override val layoutId: Int
        get() = R.layout.item_func_menu

    override fun convert(helper: BaseViewHolder, item: MultiItemEntity) {
        helper.setText(R.id.tv_title, (item as HomeDataMenu).menuParentItem.name)
        val recyclerView = helper.getView<RecyclerView>(R.id.recyclerview)
        val adapter = HomeFuncMenuItemAdapter()
        recyclerView.layoutManager = GridLayoutManager(context, AppConfig.HOME_MENU_NUM)
        adapter.setList(item.menuParentItem.subMenuList)
        recyclerView.adapter = adapter
    }
}