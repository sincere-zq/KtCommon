package com.witaction.common.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class BaseFragmentPagerAdapter(fm: FragmentManager, val fragList: MutableList<Fragment>) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment = fragList[position]

    override fun getCount(): Int = fragList.size
}