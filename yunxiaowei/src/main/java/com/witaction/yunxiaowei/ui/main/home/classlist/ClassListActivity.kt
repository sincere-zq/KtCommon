package com.witaction.yunxiaowei.ui.main.home.classlist

import android.content.Intent
import android.view.View
import androidx.lifecycle.observe
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.witaction.common.base.BVMActivity
import com.witaction.common.widget.DividerDecoration
import com.witaction.yunxiaowei.R
import com.witaction.yunxiaowei.databinding.ActivityClassListBinding

/**
 * 班级列表
 */
class ClassListActivity : BVMActivity<ActivityClassListBinding, ClassListModel>(),
    OnItemClickListener {

    companion object {
        //是否权限过滤
        const val AUTH_FILTER = "AuthFilter"
        //要跳转的登录页
        const val CLASS_ACTIVITY = "CLASS_ACTIVITY"
        //传出的班级信息
        const val CLASS_INFO = "CLASS_INFO"
    }

    private val adater by lazy { ClassAdapter() }

    override fun vmbinding(): Class<ClassListModel> = ClassListModel::class.java

    override fun viewbinding(): ActivityClassListBinding =
        ActivityClassListBinding.inflate(layoutInflater)

    override fun initView() {
        vb.headerView.setHeaderListener(this)
        vb.recyclerview.addItemDecoration(DividerDecoration(this))
        vb.recyclerview.adapter = adater
        adater.setEmptyView(getLoadingView())
        adater.setOnItemClickListener(this)
    }

    override fun initData() {
        vm.authFilter = intent.getIntExtra(AUTH_FILTER, 0)
        vm.classList.observe(this) {
            if (it.isSuccess()) {
                adater.setList(it.data)
                if (it.data.size == 0) {
                    adater.setEmptyView(getEmptyView { vm.getClassList() })
                }
            } else {
                adater.setEmptyView(getNetErrorView(it.msg) { vm.getClassList() })
            }
        }
        vm.getClassList()
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val classT = intent.getSerializableExtra(CLASS_ACTIVITY) as Class<*>
        val intent = Intent(this, classT)
        intent.putExtra(CLASS_INFO, this.adater.data[position])
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out)
    }
}
