package com.witaction.common.base

import androidx.viewbinding.ViewBinding

/**
 * Activity超类
 */
interface IActivity<VB : ViewBinding> {
    fun viewbinding(): VB
    fun initView()
    fun initData()
}