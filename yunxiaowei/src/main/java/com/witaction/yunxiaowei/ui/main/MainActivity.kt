package com.witaction.yunxiaowei.ui.main

import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.eyepetizer.android.extension.invisible
import com.eyepetizer.android.extension.visible
import com.witaction.common.base.BVMActivity
import com.witaction.common.base.BaseFragmentPagerAdapter
import com.witaction.common.extension.load
import com.witaction.common.utils.GlobalUtil
import com.witaction.plat.PlatLocalReponsitory
import com.witaction.yunxiaowei.R
import com.witaction.yunxiaowei.databinding.ActivityMainBinding
import com.witaction.yunxiaowei.framwork.AppConfig
import com.witaction.yunxiaowei.ui.main.home.HomeFragment
import com.witaction.yunxiaowei.ui.main.msg.MsgFragment
import com.witaction.yunxiaowei.ui.main.my.MyFragment

class MainActivity : BVMActivity<ActivityMainBinding, MainViewModel>(),
    ViewPager.OnPageChangeListener {

    private val homeFragment by lazy { HomeFragment() }
    private val msgFragment by lazy { MsgFragment() }
    private val myFragment by lazy { MyFragment() }

    override fun vmbinding(): Class<MainViewModel> = MainViewModel::class.java

    override fun viewbinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun initView() {
        vb.tabLayout.setupWithViewPager(vb.viewPager)
        val fragList = mutableListOf(homeFragment, msgFragment, myFragment)
        vb.viewPager.run {
            adapter = BaseFragmentPagerAdapter(
                supportFragmentManager,
                fragList
            )
            offscreenPageLimit = AppConfig.HOME_TITLES.size
            setOnPageChangeListener(this@MainActivity)
        }
        for (i in 0 until fragList.size) {
            val view = LayoutInflater.from(this).inflate(R.layout.item_tab, null)
            val tvTab = view.findViewById<TextView>(R.id.tv_tab)
            val imgTab = view.findViewById<ImageView>(R.id.img_tab)
            imgTab.setImageResource(AppConfig.HOME_ICONS[i])
            tvTab.text = AppConfig.HOME_TITLES[i]
            if (i == 0) {
                vb.tabLayout.getTabAt(i)?.setCustomView(view)?.select()
            } else {
                vb.tabLayout.getTabAt(i)?.customView = view
            }
        }
    }

    override fun initData() {
        val plat = PlatLocalReponsitory.getPlat()
        plat?.let {
            vb.tvSchoolName.text = it.name
        }
    }

    fun setSchoolLogo(schoolLogo: String) {
        vb.imgLogo.load(schoolLogo) {
            error(R.mipmap.icon_home_placeholder)
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        when (position) {
            0 -> {
                vb.imgLogo.visible()
                vb.tvSchoolName.visible()
                vb.tvTitle.invisible()
            }
            1 -> {
                vb.imgLogo.invisible()
                vb.tvSchoolName.invisible()
                vb.tvTitle.visible()
                vb.tvTitle.text = GlobalUtil.getString(R.string.message)
            }
            2 -> {
                vb.imgLogo.invisible()
                vb.tvSchoolName.invisible()
                vb.tvTitle.visible()
                vb.tvTitle.text = GlobalUtil.getString(R.string.my)
            }
        }
    }
}
