package com.witaction.common.base

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding

/**
 * Fragment超类
 */
interface IFragment<VB : ViewBinding> {
    fun viewbinding(layoutInflater: LayoutInflater): VB
    fun initView()
    fun initData()
}