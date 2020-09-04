package com.witaction.campusdefender.ui.main.home

import android.view.LayoutInflater
import androidx.lifecycle.observe
import com.witaction.campusdefender.AppConfig
import com.witaction.campusdefender.databinding.FragmentHomeBinding
import com.witaction.common.base.BVMFragment

/**
 *  首页
 */
class HomeFragment : BVMFragment<FragmentHomeBinding, HomeViewModel>() {
    private val adapter by lazy { HomeAdapter() }

    override fun vmbinding(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun viewbinding(layoutInflater: LayoutInflater): FragmentHomeBinding =
        FragmentHomeBinding.inflate(layoutInflater)

    override fun initView() {
        vb.recyclerview.adapter = adapter
        vb.refreshLayout.setEnableLoadMore(false)
        vb.refreshLayout.setOnRefreshListener {
            vm.getHomeData()
        }
        adapter.setEmptyView(getLoadingView())
    }

    override fun initData() {
        vm.loadingState.observe(this) {
            when (it) {
                AppConfig.LoadingConfig.NET_ERROR -> adapter.setEmptyView(getNetErrorView { vm.getHomeData() })
                AppConfig.LoadingConfig.EMPTY_DATA -> adapter.setEmptyView(getEmptyView { vm.getHomeData() })
                AppConfig.LoadingConfig.LOADING -> adapter.setEmptyView(getLoadingView())
                AppConfig.LoadingConfig.COMPLETE -> adapter.removeEmptyView()
            }
        }
        vm.homeDataResult.observe(this) {
            vb.refreshLayout.closeHeaderOrFooter()
            adapter.setList(it)
        }
        vm.getHomeData()
    }


}
