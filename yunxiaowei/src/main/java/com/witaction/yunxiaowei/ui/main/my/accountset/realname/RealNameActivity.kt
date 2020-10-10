package com.witaction.yunxiaowei.ui.main.my.accountset.realname

import androidx.lifecycle.observe
import com.witaction.common.base.BVMActivity
import com.witaction.common.extension.afterTextChanged
import com.witaction.common.utils.GlobalUtil
import com.witaction.common.utils.toast
import com.witaction.common.widget.ConfirmDialog
import com.witaction.yunxiaowei.R
import com.witaction.yunxiaowei.databinding.ActivityRealNameBinding
import com.witaction.yunxiaowei.framwork.LocalRepository
import com.witaction.yunxiaowei.framwork.MsgEvent
import org.greenrobot.eventbus.EventBus

/**
 * 真实姓名
 */
class RealNameActivity : BVMActivity<ActivityRealNameBinding, RealNameViewModel>() {


    override fun vmbinding(): Class<RealNameViewModel> = RealNameViewModel::class.java

    override fun viewbinding(): ActivityRealNameBinding =
        ActivityRealNameBinding.inflate(layoutInflater)

    override fun initView() {
        vb.headerView.setHeaderListener(this)
        vb.btnCommit.setOnClickListener {
            vb.etRealName.text.run {
                if (isNullOrEmpty()) {
                    toast(R.string.please_input_real_name)
                    return@setOnClickListener
                }
                saveRealName(toString())
            }
        }
        vb.etRealName.afterTextChanged {
            vb.btnCommit.isEnabled = it.isNotEmpty()
        }
    }

    override fun initData() {
        vm.userInfo.observe(this) {
            vb.etRealName.setText(it.name)
        }
        vm.saveRealNameResult.observe(this) {
            hideLoading()
            it.run {
                if (isSuccess()) {
                    vm.userInfo.value?.run {
                        name = vb.etRealName.text.toString()
                        LocalRepository.saveUserInfo(this)
                        EventBus.getDefault().post(MsgEvent.UpdateUserInfoEvent(true))
                        onBackPressed()
                    }
                } else {
                    toast(msg)
                }
            }
        }
    }

    /**
     * 提交确认
     */
    private fun saveRealName(name: String) {
        ConfirmDialog.build(this) {
            message { GlobalUtil.getString(R.string.confirm_commit) }
            cancelListener {
                {
                    it.dismiss()
                }
            }
            confirmListener {
                {
                    it.dismiss()
                    showLoading()
                    vm.saveRealName(name)
                }
            }
        }.show()
    }
}
