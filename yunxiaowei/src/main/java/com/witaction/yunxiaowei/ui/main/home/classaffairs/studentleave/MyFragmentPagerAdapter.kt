package com.witaction.yunxiaowei.ui.main.home.classaffairs.studentleave

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MyFragmentPagerAdapter(manager: FragmentManager, val fragments: List<Fragment>, val titles: List<String>) : FragmentPagerAdapter(manager) {
    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? = titles[position]
}