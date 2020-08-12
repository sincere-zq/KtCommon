package com.witaction.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Fragment基类
 */
abstract class BFragment<VB : ViewBinding> : Fragment(), IFragment<VB> {
    protected lateinit var vb: VB
    private var hasLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = viewbinding(inflater)
        return vb.root
    }

    override fun onResume() {
        super.onResume()
        if (!hasLoaded && !isHidden) {
            initView()
            initData()
            hasLoaded = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hasLoaded = false
    }

}