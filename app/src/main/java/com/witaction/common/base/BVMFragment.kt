package com.witaction.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BVMFragment<VB : ViewBinding, VM : ViewModel> : BFragment<VB>() {
    protected lateinit var vm: VM
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application)
            .create(vmbinding())
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    protected abstract fun vmbinding(): Class<VM>
}