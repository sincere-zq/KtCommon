package com.witaction.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ImmersionBar
import com.witaction.common.R
import com.witaction.common.widget.LoadingDialog

/**
 * Activity基类
 */
abstract class BActivity<VB : ViewBinding> : AppCompatActivity(), IActivity<VB> {
    private val loadingDialog by lazy { LoadingDialog(this) }
    protected lateinit var vb: VB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBar()
        AppManager.getInstance().addActivity(this)
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

    /**
     * 设置状态栏背景色
     */
    private fun setStatusBar() {
        ImmersionBar.with(this)
            .statusBarDarkFont(true, 0.2f)   //状态栏字体是深色，不写默认为亮色
            .init()
    }

    override fun onStop() {
        hideLoading()
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.getInstance().remove(this)
    }

    override fun finish() {
        super.finish()
        if (AppManager.getInstance().activityNumber() > 1) {
            overridePendingTransition(0, R.anim.slide_out_right)
        }
    }
}