package com.witaction.yunxiaowei.ui.main.home

import android.view.LayoutInflater
import androidx.lifecycle.observe
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.witaction.common.base.BVMFragment
import com.witaction.common.utils.toast
import com.witaction.yunxiaowei.HomeBanner
import com.witaction.yunxiaowei.HomeDataBanner
import com.witaction.yunxiaowei.HomeDataMenu
import com.witaction.yunxiaowei.databinding.FragmentHomeBinding
import com.witaction.yunxiaowei.ui.main.MainActivity

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
        vb.refreshlayout.setOnRefreshListener { vm.getHomeData() }
        vb.recyclerview.adapter = adapter
    }

    override fun initData() {

        adapter.setEmptyView(getLoadingView())
        vm.homeRealResultData.observe(this) {
            vb.refreshlayout.closeHeaderOrFooter()
            if (it.isSuccess()) {
                val result = it.getSimpleData()
                if (result == null) {
                    adapter.setList(mutableListOf())
                    adapter.setEmptyView(getEmptyView {
                        adapter.setEmptyView(getLoadingView())
                        vm.getHomeData()
                    })
                } else {
                    result.schoolInfo?.let { it1 ->
                        (activity as MainActivity).setSchoolLogo(it1.sLogo)
                    }

                    val datas = mutableListOf<MultiItemEntity>(
                        HomeDataBanner(
                            HomeAdapter.HOME_BANNER,
                            if (result.adList.size == 0) mutableListOf(
                                HomeBanner("", "", "")
                            ) else result.adList
                        )
                    )
                    for (item in result.menuList) {
                        datas.add(HomeDataMenu(HomeAdapter.HOME_FUNC_MENU, item))
                    }
                    adapter.setList(datas)
                }
            } else {
                toast(it.msg)
                adapter.setList(mutableListOf())
                adapter.setEmptyView(getNetErrorView(it.msg) {
                    adapter.setEmptyView(getLoadingView())
                    vm.getHomeData()
                })
            }
        }
        vm.getHomeData()
    }

}
