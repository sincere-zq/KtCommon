package com.witaction.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding

/**
 * Activity基类
 */
abstract class BActivity<VB : ViewBinding> : AppCompatActivity(), IActivity<VB> {
    protected lateinit var vb: VB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = viewbinding()
        setContentView(vb.root)
        initView()
        initData()
    }

    inline fun <reified T : Fragment> FragmentActivity.showFragment(replaceViewId: Int): T {
        val sfm = supportFragmentManager
        val transaction = sfm.beginTransaction()
        var fragment = sfm.findFragmentByTag(T::class.java.name)
        if (fragment == null) {
            fragment = T::class.java.newInstance()
            transaction.add(replaceViewId, fragment, T::class.java.name)
        }
        sfm.fragments.filter { it != fragment }
            .forEach {
                transaction.hide(it)
                transaction.setMaxLifecycle(it, Lifecycle.State.STARTED)
            }
        transaction.show(fragment)
        transaction.setMaxLifecycle(fragment, Lifecycle.State.RESUMED)
        transaction.commitAllowingStateLoss()
        sfm.executePendingTransactions()
        return fragment as T
    }
}