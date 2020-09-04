package com.witaction.yunxiaowei.ui.main.home

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.witaction.yunxiaowei.HomeBanner
import com.witaction.yunxiaowei.HomeDataBanner
import com.witaction.yunxiaowei.R
import com.zhpan.bannerview.BannerViewPager

class HomeBannerItem : BaseItemProvider<MultiItemEntity>() {
    override val itemViewType: Int
        get() = HomeAdapter.HOME_BANNER
    override val layoutId: Int
        get() = R.layout.item_home_banner

    override fun convert(helper: BaseViewHolder, item: MultiItemEntity) {
        helper.getView<BannerViewPager<HomeBanner, HomeBannerAdapter.HomeBannerViewHolder>>(
            R.id.view_pager
        ).run {
            setCanLoop((item as HomeDataBanner).bannerList.size > 0)
//                setRoundCorner(DensityUtils.dp2px(4f).toInt())
//                setRevealWidth(GlobalUtil.getDimension(R.dimen.dp_14))
//                setPageMargin(DensityUtils.dp2px(GlobalUtil.getDimension(R.dimen.dp_14).toFloat()).toInt())
            removeDefaultPageTransformer()
            adapter = HomeBannerAdapter()
            create(item.bannerList)
        }
    }
}