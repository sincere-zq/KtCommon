package com.witaction.common.base

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

/**
 * 带ViewModel的Activity基类
 */
abstract class BVMActivity<VB : ViewBinding, VM : ViewModel> : BActivity<VB>() {
    protected lateinit var vm: VM
    override fun onCreate(savedInstanceState: Bundle?) {
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(vmbinding())
        super.onCreate(savedInstanceState)
    }

    protected abstract fun vmbinding(): Class<VM>

}