package com.witaction.campusdefender.ui.main.home

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.witaction.campusdefender.R
import com.witaction.campusdefender.ui.HomeBanner
import com.witaction.campusdefender.ui.HomeBannerResult
import com.zhpan.bannerview.BannerViewPager

/**
 * 首页Banner
 */
class HomeBannerItem() : BaseItemProvider<MultiItemEntity>() {
    override val itemViewType: Int = HomeAdapter.TYPE_BANNER
    override val layoutId: Int = R.layout.item_home_banner
    override fun convert(helper: BaseViewHolder, item: MultiItemEntity) {
        helper.getView<BannerViewPager<HomeBanner, HomeBannerAdapter.HomeBannerViewHolder>>(
            R.id.view_pager
        ).run {
            setCanLoop((item as HomeBannerResult).adList.size > 0)
//                setRoundCorner(DensityUtils.dp2px(4f).toInt())
//                setRevealWidth(GlobalUtil.getDimension(R.dimen.dp_14))
//                setPageMargin(DensityUtils.dp2px(GlobalUtil.getDimension(R.dimen.dp_14).toFloat()).toInt())
            removeDefaultPageTransformer()
            adapter = HomeBannerAdapter()
            create(item.adList)
        }
    }
}