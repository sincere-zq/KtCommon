package com.witaction.yunxiaowei.ui.main.home

import com.chad.library.adapter.base.BaseProviderMultiAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity

class HomeAdapter : BaseProviderMultiAdapter<MultiItemEntity>() {
    companion object {
        const val HOME_BANNER = 0x110
        const val HOME_FUNC_MENU = 0x111
    }

    init {
        addItemProvider(HomeBannerItem())
        addItemProvider(HomeFuncMenuItem())
    }

    override fun getItemType(data: List<MultiItemEntity>, position: Int): Int = data[position].itemType
}