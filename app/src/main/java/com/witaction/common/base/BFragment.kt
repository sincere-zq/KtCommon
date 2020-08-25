package com.witaction.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import com.witaction.common.widget.LoadingDialog

/**
 * Fragment基类
 */
abstract class BFragment<VB : ViewBinding> : Fragment(), IFragment<VB> {
    private val loadingDialog by lazy { LoadingDialog(activity!!) }
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
        hideLoading()
        super.onDestroyView()
        hasLoaded = false
    }

    protected fun showLoading() {
        if (!loadingDialog.isShowing) {
            loadingDialog.setCanceledOnTouchOutside(false)
            loadingDialog.show()
        }
    }

    protected fun hideLoading() {
        loadingDialog.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }

    inline fun <reified T : Fragment> Fragment.showFragment(replaceViewId: Int): T {
        val sfm = childFragmentManager
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