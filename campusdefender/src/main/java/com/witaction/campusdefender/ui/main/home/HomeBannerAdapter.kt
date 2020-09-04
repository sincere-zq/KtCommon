package com.witaction.campusdefender.ui.main.home

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.witaction.campusdefender.R
import com.witaction.common.extension.load
import com.witaction.campusdefender.ui.HomeBanner
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder

class HomeBannerAdapter : BaseBannerAdapter<HomeBanner, HomeBannerAdapter.HomeBannerViewHolder>() {
    override fun getLayoutId(viewType: Int): Int = R.layout.item_home_image_banner

    override fun createViewHolder(itemView: View, viewType: Int): HomeBannerViewHolder =
        HomeBannerViewHolder(itemView)

    inner class HomeBannerViewHolder(itemView: View) : BaseViewHolder<HomeBanner>(itemView) {
        override fun bindData(data: HomeBanner, position: Int, pageSize: Int) {
            findView<ImageView>(R.id.img_banner).load(data.url) {
                error(R.mipmap.banner_default)
                transform(CenterCrop(), RoundedCorners(5))
            }
        }

    }

    override fun onBind(
        holder: HomeBannerViewHolder,
        data: HomeBanner,
        position: Int,
        pageSize: Int
    ) {
        holder.bindData(data, position, pageSize)
    }
}