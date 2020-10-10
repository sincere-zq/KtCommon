package com.witaction.yunxiaowei.ui.main.home.classaffairs.addstatus

import androidx.lifecycle.observe
import com.witaction.common.base.BVMActivity
import com.witaction.common.utils.GlobalUtil
import com.witaction.common.utils.toast
import com.witaction.common.widget.ConfirmDialog
import com.witaction.yunxiaowei.R
import com.witaction.yunxiaowei.StudentRoster
import com.witaction.yunxiaowei.databinding.ActivityAddStudentStatusBinding

class AddStudentStatusActivity : BVMActivity<ActivityAddStudentStatusBinding, AddStuStatusViewModel>() {

    companion object {
        const val STUDENT_INFO = "STUDENT_INFO"
    }

    override fun vmbinding(): Class<AddStuStatusViewModel> = AddStuStatusViewModel::class.java

    override fun viewbinding(): ActivityAddStudentStatusBinding = ActivityAddStudentStatusBinding.inflate(layoutInflater)

    override fun initView() {
        vb.headerView.setHeaderListener(this)
        vb.btnCommit.setOnClickListener {
            if (vb.etStuStatus.text.toString().isEmpty()) {
                toast(R.string.please_input_stu_status)
                return@setOnClickListener
            }
            confirm()
        }
    }

    override fun initData() {
        vm.studentInfo.observe(this) {
            vb.tvStuName.text = it.name
        }
        vm.updateStuStatus.observe(this) {
            hideLoading()
            if (it.isSuccess()) {
                toast(R.string.commit_success)
                onBackPressed()
            } else {
                toast(it.msg)
            }
        }
        val studentInfo = intent.getSerializableExtra(STUDENT_INFO) as StudentRoster
        vm.studentInfo.value = studentInfo
    }

    private fun confirm() {
        ConfirmDialog.build(this) {
            message { GlobalUtil.getString(R.string.confirm_commit) }
            cancelListener { { it.dismiss() } }
            confirmListener {
                {
                    it.dismiss()
                    showLoading()
                    vm.updateStuStatus(vb.etStuStatus.text.toString())
                }
            }
        }.show()
    }
}
