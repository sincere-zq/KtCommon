package com.witaction.common

import com.witaction.common.base.BActivity
import com.witaction.common.databinding.ActivityTestBinding
import com.witaction.common.widget.ConfirmDialog

class TestActivity : BActivity<ActivityTestBinding>() {

    override fun viewbinding(): ActivityTestBinding = ActivityTestBinding.inflate(layoutInflater)

    override fun initView() {
        vb.login.setOnClickListener {
            ConfirmDialog.build(this) {
                message = "确认要执行该操作吗？"
                cancelListener = {
                    it.dismiss()
                }
                confirmListener = {
                    it.dismiss()
                }
            }.show()
        }
    }

    override fun initData() {

    }
}
