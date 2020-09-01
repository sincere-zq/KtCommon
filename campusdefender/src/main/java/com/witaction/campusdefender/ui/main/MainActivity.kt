package com.witaction.campusdefender.ui.main

import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.witaction.campusdefender.AppConfig
import com.witaction.campusdefender.R
import com.witaction.campusdefender.databinding.ActivityMainBinding
import com.witaction.common.base.BVMActivity
import com.witaction.common.base.BaseFragmentPagerAdapter

/**
 * 主页
 */
class MainActivity : BVMActivity<ActivityMainBinding, MainViewModel>() {

    override fun vmbinding(): Class<MainViewModel> = MainViewModel::class.java

    override fun viewbinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun initView() {
        vb.tabLayout.setupWithViewPager(vb.viewPager)
        val fragList = mutableListOf(HomeFragment(), MessageFragment(), MyFragment())
        vb.viewPager.adapter = BaseFragmentPagerAdapter(
            supportFragmentManager,
            fragList
        )
        for (i in 0..fragList.size - 1) {
            val view = LayoutInflater.from(this).inflate(R.layout.item_tab, null)
            val tvTab = view.findViewById<TextView>(R.id.tv_tab)
            val imgTab = view.findViewById<ImageView>(R.id.img_tab)
            imgTab.setImageResource(AppConfig.HOME_ICONS[i])
            tvTab.setText(AppConfig.HOME_TITLES[i])
            if (i == 0) {
                vb.tabLayout.getTabAt(i)?.setCustomView(view)?.select()
            } else {
                vb.tabLayout.getTabAt(i)?.customView = view
            }
        }
    }

    override fun initData() {
    }
}
