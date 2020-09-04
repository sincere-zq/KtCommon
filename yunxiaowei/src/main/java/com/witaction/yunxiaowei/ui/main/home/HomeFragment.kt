package com.witaction.yunxiaowei.ui.main.home

import android.view.LayoutInflater
import androidx.lifecycle.observe
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.witaction.common.base.BVMFragment
import com.witaction.common.utils.toast
import com.witaction.yunxiaowei.HomeDataBanner
import com.witaction.yunxiaowei.HomeDataMenu
import com.witaction.yunxiaowei.databinding.FragmentHomeBinding

/**
 * 首页
 */
class HomeFragment : BVMFragment<FragmentHomeBinding, HomeViewModel>() {
    private val adapter by lazy { HomeAdapter() }
    override fun vmbinding(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun viewbinding(layoutInflater: LayoutInflater): FragmentHomeBinding =
        FragmentHomeBinding.inflate(layoutInflater)

    override fun initView() {
        vb.refreshlayout.setEnableLoadMore(false)
        vb.refreshlayout.setOnRefreshListener { }
        vb.recyclerview.adapter = adapter
    }

    override fun initData() {
        vm.homeDataResult.observe(this) {
            vb.refreshlayout.closeHeaderOrFooter()
            if (it.isSuccess()) {
                it.getSimpleData()?.let { it1 ->
                    val datas = mutableListOf<MultiItemEntity>(
                        HomeDataBanner(
                            HomeAdapter.HOME_BANNER,
                            it1.adList
                        ), HomeDataMenu(HomeAdapter.HOME_FUNC_MENU, it1.menuList)
                    )
                    adapter.setList(datas)
                }
            } else {
                toast(it.msg)
                adapter.setList(mutableListOf())
                adapter.setEmptyView(getNetErrorView { vm.getHomeData() })
            }
        }
    }

}
